package com.building.cache;


import com.building.dto.AuthorizedUserInfo;

public interface AuthorizedUserTokenCache {
	public AuthorizedUserInfo findToken(String token);
	public void registerUserInfoToken(AuthorizedUserInfo userInfo);
	public AuthorizedUserInfo removeToken(String token);

}
