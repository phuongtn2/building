package com.building.util;

import com.building.dto.AuthorizedUserInfo;
import com.building.services.error.ServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ApiRelationAuthorizer {
	private final Map<String, AuthorizedUserInfo> tokenMap;
	private final String errorCode;

	public ApiRelationAuthorizer(String jsonDef, String errorCode) throws IOException {
		this(parseJson(jsonDef), errorCode);
	}

	public ApiRelationAuthorizer(Map<String, AuthorizedUserInfo> tokenMap, String errorCode) {
		this.tokenMap = Collections.unmodifiableMap(tokenMap);
		this.errorCode = errorCode;
	}

	public AuthorizedUserInfo checkAuthorized(String token) throws ServiceException {
		if(tokenMap.containsKey(token)) {
			return tokenMap.get(token);
		} else {
			throw new ServiceException(errorCode);
		}
	}

	protected static Map<String, AuthorizedUserInfo> parseJson(String jsonDef) throws IOException {
		final HashMap<String, AuthorizedUserInfo> map = new HashMap<String, AuthorizedUserInfo>();

		final ObjectMapper om = new ObjectMapper();
		@SuppressWarnings("unchecked")
		final Map<String, ?> json = (Map<String, ?>) om.readValue(jsonDef, Map.class);
		for (final Map.Entry<String, ?> me : json.entrySet()) {
			final AuthorizedUserInfo aui = parseAuthorizedUserInfo(me.getValue());
			if (aui != null) {
				map.put(me.getKey(), aui);
			}
		}
		return map;
	}

	protected static AuthorizedUserInfo parseAuthorizedUserInfo(Object value) {
		if(value instanceof Map) {
			final Map<?,?> m = (Map<?,?>) value;
			final AuthorizedUserInfo aui = new AuthorizedUserInfo();
			aui.setUserId(toInt( m.get("userId"),0));
			return aui;
		}
		return null;
	}

	protected static int toInt(Object object, int defaultValue) {
		if(object!=null) {
			if(object instanceof Number) {
				return ((Number)object).intValue();
			} else {
				try {
					return Integer.parseInt(object.toString());
				} catch (NumberFormatException e) {
				}
			}
		}
		return defaultValue;
	}
}
