package com.building.util.client.dto;

/**
 * PowerCMSの認証オブジェクト
 * @author kohei.takumi
 *
 */
public class PowerCMSAuthDto {

	private String sessionId;
	private String accessToken;
	private int expiresIn;
	private String remember;

	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getRemember() {
		return remember;
	}
	public void setRemember(String remember) {
		this.remember = remember;
	}

	
}
