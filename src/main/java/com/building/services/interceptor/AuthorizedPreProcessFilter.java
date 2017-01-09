package com.building.services.interceptor;

import com.building.dto.AuthorizedUserInfo;
import com.building.services.ApiDefs;
import com.building.services.AuthorizedUserTokenService;
import com.building.services.Role;
import com.building.services.annotation.AllowRoles;
import com.building.services.annotation.ParameterTokenSupport;
import com.building.services.annotation.PermissionBinding;
import com.building.util.RestMessageUtil;
import com.building.util.core.ClassUtil;
import org.apache.log4j.Logger;
import org.jboss.resteasy.core.interception.PostMatchContainerRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

@Provider
@PermissionBinding
@Component
public class AuthorizedPreProcessFilter implements ContainerRequestFilter, ContainerResponseFilter {
	/**
	 * ログ出力(認証フィルター用)
	 */
	private static final Logger log = Logger.getLogger(AuthorizedPreProcessFilter.class);

	/**
	 * ロール確認用のアノテーションクラス
	 */
	private static final Class<AllowRoles> ALLOW_ANN = AllowRoles.class;
	/**
	 * ロール確認用のアノテーションクラス(パラメータの内容も評価対象)
	 */
	private static final Class<ParameterTokenSupport> PARAM_ANN = ParameterTokenSupport.class;

	/**
	 * 認証サービス
	 */
	@Autowired
	private AuthorizedUserTokenService authService;
	/**
	 * リソースメッセージ
	 */
	@Autowired
	private ResourceBundleMessageSource messageSource;

	/**
	 * 認証用フィルター(リクエスト時).
	 * <p>
	 * 1. 手動認証RESTリソースの場合は認証処理を行わない
	 * 2. ヘッダーのトークンが設定されている場合はトークンからユーザロール情報を検索する
	 * 3. トークンのユーザロールがない場合は、SSOからユーザIDを特定し、DBからユーザロール検索を行う
	 * 4. SSOからもユーザロールがわからない場合は認証エラーを返す
	 * 5. ユーザロール情報はプロパティに保存する（レスポンスヘッダー保存用）
	 * 6. ユーザロール情報から実行するAPIのロール確認を実行する。
	 *
	 * @param req Request information.
	 * @throws IOException 処理時IO例外
	 */
	@Override
	public void filter(ContainerRequestContext req) throws IOException {

		final List<Object> resources = req.getUriInfo().getMatchedResources();
		if (ClassUtil.findByDescendantClass(resources, AuthorizedUserTokenService.class)) {//REST実行対象に手動認証のApiはフィルター対象外
			//手動認証をするRESTの部品の場合は前処理なし 実際はフィルター対象外なのでこのコードには入らない
			log.trace("手動認証処理のため、トークン照合/SSO処理をスキップします");
			return;//トークン復元や権限確認の必要がないので処理を抜ける
		}


		final MultivaluedMap<String, String> mm = req.getHeaders();
		AuthorizedUserInfo aui = null;
		//ヘッダーのトークン情報で既存を確認する
		if (mm.containsKey(ApiDefs.TOKEN_HEADER_NAME)) {
			final String tokenValue = mm.getFirst(ApiDefs.TOKEN_HEADER_NAME);
			aui = authService.checkAuthorizedUserInfo(tokenValue);
			log.debug("トークン照合:" + tokenValue + "->" + ((aui == null) ? "(なし)" : aui.getLoginName()));
		}

		// メソッドアノテーション配列の取得
		Annotation[] annons = null;
		if (req instanceof PostMatchContainerRequestContext) {
			//ロールの確認(メソッド) RESTEASYに依存あり JAX-RSの範囲でやりうる方法を検討
			annons = ((PostMatchContainerRequestContext) req).getResourceMethod().getMethodAnnotations();
		}
		if (aui == null && findMethodAllowRole(annons, PARAM_ANN) != null) {//ヘッダーに認証情報がない場合、特定のアノテーションメソッドでパラメータトークン復元を試みる
			final MultivaluedMap<String, String> pmm = req.getUriInfo().getQueryParameters();
			final String tokenValue = pmm.getFirst(ApiDefs.TOKEN_PARAMETER_NAME);
			aui = authService.checkAuthorizedUserInfo(tokenValue);
			log.debug("トークン照合(パラメータ):" + tokenValue + "->" + ((aui == null) ? "(なし)" : aui.getLoginName()));
//			abortResponse(req, e.getMessage());//ログイン権限が許可されないか、指定事業部の利用が許可されていない場合
			return;
		}
		if(aui != null){
			req.setProperty(ApiDefs.AUTH_PROP_NAME, aui);
		}
		//ロールの確認(メソッド・インターフェース)
		final AllowRoles methodAllowRoles = findMethodAllowRole(annons, ALLOW_ANN);
		if (methodAllowRoles != null) {//メソッド
			if (!hasRequireRole(aui, methodAllowRoles.roles())) {
				abortResponse(req, "roleBlockMethod");
			}
		} else {//インターフェース
			final AllowRoles interfaceAllowRoles = findInterfaceAllowRole(req);
			if (interfaceAllowRoles != null && (!hasRequireRole(aui, interfaceAllowRoles.roles()))) {
				abortResponse(req, "roleBlockInterface");
			}
		}
	}

	/**
	 * 認証用フィルター(レスポンス時).
	 * <p>
	 * 読み込みフィルター保持されたトークンの情報を応答ヘッダーで返す(DB検索直後のみ設定)
	 *
	 * @param req リクエストコンテキスト
	 * @param res レスポンスコンテキスト
	 * @throws IOException 処理時IO例外
	 */
	@Override
	public void filter(ContainerRequestContext req, ContainerResponseContext res)
			throws IOException {
		final Object obj = req.getProperty(ApiDefs.AUTH_PROP_NAME);
		if (obj instanceof AuthorizedUserInfo) {
			final AuthorizedUserInfo aui = (AuthorizedUserInfo) obj;
			final MultivaluedMap<String, Object> mm = res.getHeaders();
			authService.storeResponseHeader(mm, aui);
		}
	}

	/**
	 * 指定されたアノテーションクラスがアノテーション配列に含まれるかを確認する.
	 *
	 * @param annons メソッドまたはクラスに付加されたアノテーション配列
	 * @param target 存在するかを確認する対象のアノテーションクラス
	 * @return 存在した場合は、最初に発見したアノテーション。存在しない場合はnull
	 */
	protected static <T> T findMethodAllowRole(Annotation[] annons, Class<T> target) {
		if (annons != null) {
			final T ar = ClassUtil.lookupAnnotation(annons, target);
			if (ar != null) {
				return ar;
			}
		}
		return null;
	}

	protected AllowRoles findInterfaceAllowRole(ContainerRequestContext crc) {
		//ロールの確認(インタフェース)
		for (final Object r : crc.getUriInfo().getMatchedResources()) {
			final AllowRoles ar = ClassUtil.findInterfaceAnnotation(r.getClass(), ALLOW_ANN);
			if (ar != null) {
				return ar;
			}
		}
		return null;
	}

	/**
	 * 認証エラー時のレスポンス内容.
	 *
	 * @param crc         リクエストコンテキスト
	 * @param messageCode メッセージコード
	 */
	protected void abortResponse(ContainerRequestContext crc, String messageCode) {
		final Response r = RestMessageUtil.makeResponse(messageSource, messageCode);
		crc.abortWith(r);
	}

	/**
	 * 社員ロールに、APIの要求ロールのいずれかが一致するかの確認する.
	 *
	 * @param aui          社員ロール情報
	 * @param requireRoles 実行要求されるロール(ロール指定が空の場合は誰も実行できない)
	 * @return 実行要求されるロール群のいずれかのロールが社員ロールに含まれる場合にtrue/含まれない場合false
	 */
	protected static boolean hasRequireRole(AuthorizedUserInfo aui, Role[] requireRoles) {
		if (requireRoles != null) {
			for (final Role role : requireRoles) {
				if (aui.getRoleSet().contains(role)) {
					return true;
				}
			}
		}
		return false;
	}

}
