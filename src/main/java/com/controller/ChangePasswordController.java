package com.controller;

import com.building.dto.login.AuthorizedUserInfo;
import com.building.dto.login.UserDto;
import com.building.services.AuthorizedUserTokenService;
import com.building.services.UserService;
import com.building.services.error.ServiceException;
import com.dropbox.core.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;

@Controller
public class ChangePasswordController {
	@Autowired
	private AuthorizedUserTokenService authorizedUserTokenService;

	@Autowired
	private UserService userService;

	@RequestMapping(value ="/changepass")
	public String initForm(ModelMap model) throws ServiceException {
		return "changePassword";
	}

//	@RequestMapping(value ="/saveNewPassword", method = RequestMethod.POST)
	@RequestMapping( value ="/saveNewPassword", method = RequestMethod.POST)
	public String saveNewPassword(@ModelAttribute("userChangePassDto") UserDto userDto, HttpServletRequest request) throws ServerException {
		AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
		userDto.setUserId(aui.getUserId());
		userService.updatePassword(userDto);
//		return "changePasswordSuccess";
		return "redirect:/user";
	}

}
