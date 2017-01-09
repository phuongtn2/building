package com.building.util;

import com.building.dto.AuthorizedUserInfo;
import com.building.services.ApiDefs;
import com.building.services.Role;
import com.building.services.error.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class AuthorizeUtil {
	/**
	 * Get the stored authenticated information.
	 * @param req Request information.
	 * @return Stored authenticated information.
	 */
	public static AuthorizedUserInfo getAuthorizedUserInfo(HttpServletRequest req) {
		return (AuthorizedUserInfo) req.getAttribute(ApiDefs.AUTH_PROP_NAME);// Authenticated information
	}

	public static AuthorizedUserInfo requireAuthorizedUserInfo(HttpServletRequest req) throws ServiceException {
		final AuthorizedUserInfo aui = getAuthorizedUserInfo(req);
		if (aui == null) {
			throw new ServiceException("requireAuth");
		} else {
			if (! aui.getRoleSet().contains(Role.ADMIN) && ! aui.getRoleSet().contains(Role.MEMBER)) {
				throw new ServiceException("requireLoginRole", aui.getLoginName(), aui.getUserId());
			}
			return aui;
		}
	}
}
