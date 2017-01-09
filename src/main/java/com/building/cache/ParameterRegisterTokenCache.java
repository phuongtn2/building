package com.building.cache;


import com.building.services.error.ServiceException;

public interface ParameterRegisterTokenCache {

	public String pushEntity(Object obj) throws ServiceException;

	public Object popEntity(String token) throws ServiceException;
}
