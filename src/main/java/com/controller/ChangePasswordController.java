package com.controller;

import com.building.services.AuthorizedUserTokenService;
import com.building.services.error.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChangePasswordController {
	@Autowired
	private AuthorizedUserTokenService authorizedUserTokenService;

	@RequestMapping(value = "/changepass")
	public String initForm(ModelMap model) throws ServiceException {
		return "changePassword";
	}
}
