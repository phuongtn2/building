package com.building.services;

import com.building.dto.AuthorizedUserInfo;
import com.building.services.error.ServiceException;

import javax.ws.rs.core.MultivaluedMap;
import java.security.Principal;

public interface AuthorizedUserTokenService {
	AuthorizedUserInfo checkAuthorizedUserInfo(String token);
	AuthorizedUserInfo findAuthorizedUserInfoByEmployeeId(int employeeId) throws ServiceException;
	String logoutAuthorizedUserInfo(String token);
	String findSsoAuthorizedUserId(Principal p) throws ServiceException;
	void storeResponseHeader(MultivaluedMap<String, Object> mm, AuthorizedUserInfo aui);
	void checkAuthorized(AuthorizedUserInfo aui) throws ServiceException;
	//AuthorizedUserInfo checkGoogleAuthorizedUserInfo(String accessToken, String token,
	// String remoteAddr, String remoteHost) throws ServiceException;
	AuthorizedUserInfo doLogin(String adId, String password) throws ServiceException;

}
