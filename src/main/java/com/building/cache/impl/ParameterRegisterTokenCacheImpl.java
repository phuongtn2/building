package com.building.cache.impl;

import com.building.cache.ParameterRegisterTokenCache;
import com.building.services.error.ServiceException;
import com.building.util.str.TokenGenerator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ParameterRegisterTokenCacheImpl implements ParameterRegisterTokenCache {
	private final HashMap<String, Object> cacheMap = new HashMap<String, Object>();

	private final HashMap<String, Long> expireTreeMap = new HashMap<String, Long>();

	private int maxRetry = 1000;
	private long expireInterval = 300000L;
	private long cleaningMinInterval = 60000L;
	private long previous = System.currentTimeMillis();

	@Override
	public String pushEntity(Object obj) throws ServiceException {
		final long now = System.currentTimeMillis();
		claening(now);
		for(int i=0; i<maxRetry; i++) {
			final String token = newToken();
			synchronized (cacheMap) {
				if(! cacheMap.containsKey(token)) {
					cacheMap.put(token, obj);
					expireTreeMap.put(token, now+expireInterval);
					return token;
				}
			}
		}
		throw new ServiceException("parameterCache.random.generation.timeout");
	}

	@Override
	public Object popEntity(String token) throws ServiceException {
		claening(System.currentTimeMillis());

		synchronized (cacheMap) {
			if(cacheMap.containsKey(token)) {
				final Object value = cacheMap.remove(token);
				expireTreeMap.remove(token);
				return value;
			} else {
				throw new ServiceException("parameterCache.nosuchToken");
			}
		}
	}

	protected void claening(long now) {
		if (now > previous + cleaningMinInterval) { //最終実行時間から最小実行間隔が経過している
			synchronized (cacheMap) {
				previous = now;//最終実行時刻を更新

				for(final Map.Entry<String, Long> me : expireTreeMap.entrySet()) {
					if(me.getValue()<now) {
						final String token = me.getKey();
						cacheMap.remove(token);
						expireTreeMap.remove(token);
					} else {
						break;
					}
				}
			}
		}
	}

	protected static String newToken() {
		return TokenGenerator.createRandomAkphaNum(20);
	}
}
