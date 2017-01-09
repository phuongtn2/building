package com.building.services.impl;

import com.building.cache.AuthorizedUserTokenCache;
import com.building.dto.AuthorizedUserInfo;
import com.building.mapper.AuthorizedUserTokenMapper;
import com.building.services.ApiDefs;
import com.building.services.AuthorizedUserTokenService;
import com.building.services.Role;
import com.building.services.error.ServiceException;
import com.building.util.net.SslSocketFactory;
import com.building.util.str.NumConverter;
import com.building.util.str.StringUtil;
import com.building.util.str.TokenGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.ws.rs.core.MultivaluedMap;
import java.security.Principal;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;

@Service
public class AuthorizedUserTokenServiceImpl implements
		AuthorizedUserTokenService {
	private static final Logger log = Logger.getLogger(AuthorizedUserTokenServiceImpl.class);
	@Resource
	private Properties setting;

	@Autowired
	private AuthorizedUserTokenCache authCache;

	@Autowired
	private AuthorizedUserTokenMapper authMapper;

	@Autowired
	private ApplicationContext applicationContext;
	private int randomTokenLength = 20;
	private String ldapAuthorizedUrl;
	private String ldapAdDomainName;
	@PostConstruct
	public void initPathPatternList() {
		randomTokenLength = NumConverter.parseIntProperty(setting, "authorized.token.randomLength", randomTokenLength);
		ldapAuthorizedUrl = setting.getProperty("authorized.ldap.url");// ldap://192.178.120.1
		ldapAdDomainName = setting.getProperty("authorized.ldap.domain", ""); // "@open.local"

		final String keystoreDefKey = "authorized.ldap.ssl.keystore.filename";
		if(setting.containsKey(keystoreDefKey)) {
			final String pass = setting.getProperty("authorized.ldap.ssl.keystore.password", "");
			SslSocketFactory.init(applicationContext.getResource(setting.getProperty(keystoreDefKey)), pass);
			//loadKeystore(applicationContext.getResource(setting.getProperty(keystoreDefKey)), pass);
		}
	}

	@Override
	public AuthorizedUserInfo checkAuthorizedUserInfo(String token) {
		return authCache.findToken(token);
	}

	protected boolean ldapLoginCheck(String userName, String password) throws ServiceException {
		if(StringUtils.isEmpty(ldapAuthorizedUrl)) {
			throw new ServiceException("notAcceptLDAPAuth");
		}

		final Hashtable<String, String> env = new Hashtable<>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.PROVIDER_URL, ldapAuthorizedUrl);
		env.put(Context.SECURITY_PRINCIPAL, userName + ldapAdDomainName);
		env.put(Context.SECURITY_CREDENTIALS, password);
		if(ldapAuthorizedUrl.startsWith("ldaps:")) {
			env.put("java.naming.ldap.factory.socket", "phuongtn2.util.net.SslSocketFactory");
			env.put(Context.SECURITY_PROTOCOL, "ssl");
		}
		DirContext ctx;
		try {
		    ctx = new InitialDirContext(env);
		    ctx.close();
		    return true;
		} catch (NamingException ex) {
			ex.printStackTrace();
		}
		return false;
	}
//	protected void loadKeystore(org.springframework.core.io.Resource keyResource, String keypass) {
//		try {
//			char[] storePass = keypass.toCharArray();
//			KeyStore keyStore = KeyStore.getInstance("JKS");
//
//			try (InputStream is = keyResource.getInputStream()) {
//				keyStore.load(is, storePass);
//			}
//
//			KeyManagerFactory keyManFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//			keyManFactory.init(keyStore, storePass);
//
//			TrustManagerFactory trustManFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//			trustManFactory.init(keyStore);
//
//			KeyManager[] keyManager = keyManFactory.getKeyManagers();
//			TrustManager[] trustManager = trustManFactory.getTrustManagers();
//
//			SSLContext sslContext = SSLContext.getInstance("SSL");
//			sslContext.init(keyManager, trustManager, null);
//			SSLContext.setDefault(sslContext);
//		} catch (Exception e) {
//			throw new RuntimeException("Failed to load KeyStore", e);
//		}
//	}
	@Override
	public AuthorizedUserInfo findAuthorizedUserInfoByEmployeeId(int employeeId) throws ServiceException {
		final AuthorizedUserInfo userInfo = authMapper.findAuthorizedUserInfoByEmployeeId(employeeId);
		if (userInfo != null) {
			registerUserInfoUserInfo(userInfo);
		} else {
			throw new ServiceException("noEmpIdUser", employeeId);
		}
		return userInfo;
	}
	@Override
	public String findSsoAuthorizedUserId(Principal p) throws ServiceException {
		if(p!=null) {
			return p.getName();
		}
		throw new ServiceException("noSSOUser");
	}
	public void checkAuthorized(AuthorizedUserInfo aui) throws ServiceException {
		if(StringUtil.isEmpty(aui.getFullName())) {
			throw new ServiceException("Not Login");
		}
	}

	public AuthorizedUserInfo doLogin(String adId, String password) throws ServiceException {
		AuthorizedUserInfo userInfo = authMapper.findAuthorizedUserInfoByAuth(
				adId, password);
		if (userInfo != null) {
			log.info("adId:" + adId);
			registerUserInfoUserInfo(userInfo);
		}
		return userInfo;
	}

	/*@Override
	public AuthorizedUserInfo checkGoogleAuthorizedUserInfo(String accessToken, String token, String remoteAddr, String remoteHost) throws ServiceException {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
				.setAudience(Collections.singletonList(setting.getProperty("authorized.google.client_id")))
				.setIssuer("accounts.google.com")
				.build();

		GoogleIdToken idToken = null;
		try {
			idToken = verifier.verify(token);
		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
		}
		if (idToken != null) {
			GoogleIdToken.Payload payload = idToken.getPayload();

			// Get profile information from payload
			final String email = payload.getEmail(); 
			final AuthorizedUserInfo userInfo = authMapper.findAuthorizedUserInfoByEmail(email);
			if (userInfo == null) {
				throw new ServiceException("noDBGoogleUser", email);
			} else {
				userInfo.setIpAddress(remoteAddr);
				userInfo.setComputerName(remoteHost);
				userInfo.setGoogleToken(accessToken);
				// related authentication information processing
				registerUserInfoUserInfo(userInfo);
			}
			return userInfo;
		}
		throw new ServiceException("noGoogleUserData");
	}*/

	protected void registerUserInfoUserInfo(AuthorizedUserInfo userInfo)  {

		final int userId = userInfo.getUserId();
		// search and setup user roles
		userInfo.setRoleSet(Role.toRoleList(authMapper.findUserRoleList(userId)));
        final Date createAt = new Date();
        // generate authenticate token
        final String token = TokenGenerator.create(createAt, userInfo.getUserId(), randomTokenLength);
        userInfo.setToken(token);
        userInfo.setCreateAt(createAt);
        // and save it into cache
        authCache.registerUserInfoToken(userInfo);
        userInfo.setNew(true);
	}
	@Override
	public void storeResponseHeader(MultivaluedMap<String, Object> mm, AuthorizedUserInfo aui) {
		if (aui.isNew()) {
			mm.add(ApiDefs.TOKEN_HEADER_NAME, aui.getToken());
			mm.add(ApiDefs.LOGIN_USER_ID_HEADER_NAME, String.valueOf(aui.getUserId()));
			mm.add(ApiDefs.LOGIN_USER_NAME_HEADER_NAME, StringUtil.base64EncodeString(aui.getFullName()));
			mm.add(ApiDefs.ROLE_LIST_HEADER_NAME,
					Role.toHeaderRoleListValue(aui.getRoleSet()));
			mm.add(ApiDefs.AUTH_PROP_NAME, aui);
		}
	}
	@Override
	public String logoutAuthorizedUserInfo(String token) {
		final AuthorizedUserInfo removed = authCache.removeToken(token);
		return removed != null ? String.valueOf(removed.getLoginName()) : "PhuongTN2";
	}

	public void setRandomTokenLength(int len) {
		randomTokenLength = len;
	}

}
