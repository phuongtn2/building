package com.building.cache.impl;

import com.building.cache.AuthorizedUserTokenCache;
import com.building.dto.AuthorizedUserInfo;
import com.building.services.Role;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service

public class AuthorizedUserTokenCacheImpl implements AuthorizedUserTokenCache {

	private final HashMap<String, AuthorizedUserInfo> cacheMap = new HashMap<String, AuthorizedUserInfo>();
	private long expireInterval = 86400000L;
	private long cleaningMinInterval = 600000L;
	private long previous = System.currentTimeMillis();

	@Override
	public AuthorizedUserInfo findToken(String token) {
		final long now = System.currentTimeMillis();
		synchronized (cacheMap) {
			clean(now);
			return cacheMap.get(token);
		}
	}

	@Override
	public void registerUserInfoToken(AuthorizedUserInfo userInfo) {
		final AuthorizedUserInfo caui = userInfo.deepClone();
		final String token = caui.getToken();
		synchronized (cacheMap) {
			cacheMap.put(token, caui);
		}
	}

	@Override
	public AuthorizedUserInfo removeToken(String token) {
		synchronized (cacheMap) {
			return cacheMap.remove(token);
		}
	}

	protected void clean(long now) {
		if (now > previous + cleaningMinInterval) {
			previous = now;
			final long at = now - expireInterval;
			final LinkedList<String> removeKeyList = new LinkedList<String>();
			for (final Map.Entry<String, AuthorizedUserInfo> em : cacheMap.entrySet()) {
				final AuthorizedUserInfo aui = em.getValue();
				if (aui.getCreateAt().getTime() < at) {
					removeKeyList.add(em.getKey());
				}
			}
			for (final String key : removeKeyList) {
				cacheMap.remove(key);
			}
		}
	}

	//DI
	public void setExpireInterval(long interval) {
		expireInterval = interval;
	}

	//DI
	public void setcleaningMinInterval(long cleaningMinInterval) {
		this.cleaningMinInterval = cleaningMinInterval;
	}

	@PostConstruct
	public void registerUserInfoBypassAuthToken() {
		synchronized (cacheMap) {
			final AuthorizedUserInfo aui1 = makeAuthorizedUserInfo(//
					101, "adUser1", "A1", false, //
					new Date(), "DUMMY_ROLE_GROUP1", Role.MEMBER, Role.ADMIN);
			registerUserInfoToken(aui1);
		}
	}
	private static AuthorizedUserInfo makeAuthorizedUserInfo(int userID, String adId, String token, boolean isNew, Date createAt, String roleGroupName, Role... roles) {
		final AuthorizedUserInfo aui = new AuthorizedUserInfo();
		aui.setUserId(userID);
		aui.setLoginName(adId);
		aui.setToken(token);
		aui.setNew(isNew);
		aui.setCreateAt(createAt);
		aui.setRoleGroupName(roleGroupName);
		if (roles != null) {
			aui.setRoleSet(Arrays.asList(roles));
		}
		return aui;
	}
}
