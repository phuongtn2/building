package com.building.services.interceptor;

import com.building.dto.JsonErrorDto;
import com.building.services.error.ServiceException;
import com.building.util.RestMessageUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.text.MessageFormat;
import java.util.Locale;

@Provider
@Component
public class GhDefaultExceptionMapper implements ExceptionMapper<Throwable> {
	/** ログ出力(APIサーバ汎用) */
	private static final Logger log = Logger.getLogger(GhDefaultExceptionMapper.class);
	/** エラー内容出力 */
	private static final MessageFormat mf = new MessageFormat("API実行でエラーが発生しました。HTTP:{0} コード:{1} メッセージ:{2}");
	/** パラメータ引数なし定数 */
	public static final Object[] NO_ARGUMENT = new Object[0];
	/** リソースメッセージ */
	@Autowired
	private ResourceBundleMessageSource messageSource;

	private final Locale defaultLocale = Locale.getDefault();

	@Override
	public Response toResponse(Throwable e) {
		final JsonErrorDto dto;
		Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
		if(e instanceof WebApplicationException) {
			final WebApplicationException wae = (WebApplicationException) e;
			return wae.getResponse();
// エラーコードの出力が必要な場合は以下のような形で発行する
//			final NotFoundException nfe = (NotFoundException) e;
//			status = Response.Status.NOT_FOUND;
//			dto  = new JsonErrorDto();
//			dto.setStatusCode("99997");
//			dto.setMessage(nfe.getLocalizedMessage());
		} else if(e instanceof ServiceException) {
			final ServiceException ghe = (ServiceException) e;

			if(ghe.getError()!=null) {
				dto = ghe.getError();
			} else {
				dto = new JsonErrorDto();
				final String prefix = ghe.getMessage();
				//ステータスを確定(Enum文字列で指定されている必要あり。一致しない場合は500エラーで返す)
				try {
					final String ss = messageSource.getMessage(prefix+".status", NO_ARGUMENT, defaultLocale);
					if(ss!=null) {
						final Response.Status rs = Response.Status.valueOf(ss);
						status = rs;
					}
				} catch (NoSuchMessageException|IllegalArgumentException e1) {//ステータスコード指定なし OR ステータスコードがEnumに一致しない
					status = Response.Status.INTERNAL_SERVER_ERROR;
				}


				try {
					final String statusCode = messageSource.getMessage(prefix + ".code", NO_ARGUMENT, defaultLocale);
					final String message = messageSource.getMessage(prefix + ".message", RestMessageUtil.formatObjectArray(ghe.getArguments()), defaultLocale);
					dto.setStatusCode(statusCode);
					dto.setMessage(message);
				} catch (NoSuchMessageException e1) {
					dto.setStatusCode("99998");
					dto.setMessage(prefix);
				}
			}
		} else {
			dto  = new JsonErrorDto();
			dto.setStatusCode("99999");
			dto.setMessage(MessageFormat.format("API内部で想定していないエラーが発生しました({0}::{1})", e.getClass().getName(), e.getMessage()));
		}

		log.error(mf.format(new Object[] {status.toString(), dto.getStatusCode(), dto.getMessage()}), e);
		return Response.status(status).type(MediaType.APPLICATION_JSON).entity(dto).build();
	}
}
