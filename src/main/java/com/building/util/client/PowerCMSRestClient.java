package com.building.util.client;

import com.building.services.error.ServiceException;
import com.building.util.client.dto.PowerCMSAuthDto;
import com.building.util.str.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PowerCMSRestClient {

	/** PowerCMSサーバー */
	private final String server = "http://10.0.66.132/mt/mt-data-api.cgi/";
	/** APIのバージョン */
	public final String API_VER = "v2/";
	/** APIのユーザ名 */
	private final String USERNAME = "sfa";
	/** APIのパスワード */
	private final String PASSWORD = "F8Qa2Nr9XQGB";
	/** APIのクライアントID */
	private final String CLIENT_ID = "sfa";

	/** パス：認証 */
	public final String PATH_AUTH = API_VER + "authentication" + "?username=" + USERNAME +"&password=" + PASSWORD + "&remember=0&clientId=" + CLIENT_ID;
	/** パス：トークン */
	public final String PATH_TOKN = API_VER + "token";
	/** パス：会員ステータス更新 */
	public final String PATH_UMS = API_VER + "openhouse/update_member_status";

	/** HPとSFAのメンバーステータスの違い */
	private final int GH_HP_DIFFERENCE = -1; 

	/** 認証情報 */
	PowerCMSAuthDto auth = null;

	/** HTTPメソッド定義 */
	private enum HTTP_METHOD {
		POST,
		PUT,
		DELETE
	}

	/**
	 * 会員ステータスを更新する。
	 * @param memberId
	 * @param memberStatus
	 * @param quitFlag
	 * @throws ServiceException
	 */
	public void updateMemberStatus(long memberId, int memberStatus, int quitFlag) throws ServiceException {
		// 認証情報を受け取る。
		try {
			connect(HTTP_METHOD.POST, PATH_AUTH, null);
			connect(HTTP_METHOD.PUT, PATH_UMS, createStatusParam(memberId, memberStatus, quitFlag));
		} catch (ServiceException e) {
			throw e;
		} finally {
			connect(HTTP_METHOD.DELETE, PATH_TOKN, null);
		}
	}

	/**
	 * 
	 * @param method
	 * @param path
	 * @param requestBody
	 */
	private void connect(HTTP_METHOD method, String path, String requestBody) throws ServiceException {
		HttpURLConnection httpConnection = null;
		try {
			URL targetUrl = new URL(server + path);

			httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod(method.toString());
			if (hasAuth(auth)) {
				httpConnection.setRequestProperty("X-MT-Authorization", "MTAuth accessToken=" + auth.getAccessToken());
			}

			if (!StringUtil.isEmpty(requestBody)) {
				OutputStreamWriter out = null;
				try {
					out = new OutputStreamWriter(httpConnection.getOutputStream());
					out.write(requestBody);
				} catch(Exception e) {
					e.printStackTrace();
				} finally {
					if (out != null) {
						try { out.close(); } catch(Exception e) {};
					}
				}
			}

			if (httpConnection.getResponseCode() != 200) {
				throw new ServiceException("Failed : HTTP error code : " + httpConnection.getResponseCode());
			}
			if (auth == null) {
				// JSONパース用
				ObjectMapper mapper = new ObjectMapper();
				auth = mapper.readValue(httpConnection.getInputStream(), PowerCMSAuthDto.class);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpConnection != null) {
				try { httpConnection.disconnect();} catch(Exception e) {};
			}
		}
	}

	/**
	 * 認証情報を持っているかどうかをチェックする。
	 * @param auth
	 * @return
	 * @throws ServiceException
	 */
	private boolean hasAuth(PowerCMSAuthDto auth) {
		return !(auth == null || StringUtil.isEmpty(auth.getAccessToken()));
	}

	private String createStatusParam(long memberId, int memberStatus, int quitFlag) {
		// SFAとHPのステータスの差を吸収する。
		memberStatus += GH_HP_DIFFERENCE;
		return "author=" + "{\"quit_flag\":" + quitFlag + ",\"premium_flag\":" + memberStatus +",\"id\":" + memberId + "}";
	}
}
