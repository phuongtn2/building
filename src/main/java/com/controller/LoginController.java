package com.controller;

import com.building.dto.AuthorizedUserInfo;
import com.building.services.AuthorizedUserTokenService;
import com.building.services.error.ServiceException;
import com.building.util.str.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;

@Controller
@SessionAttributes
public class LoginController {
	@Autowired
	private AuthorizedUserTokenService authorizedUserTokenService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String initForm(ModelMap model) throws ServiceException {
		return "login";
	}
	@RequestMapping("/login")
	public ModelAndView login(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		ResourceBundle bundle = ResourceBundle.getBundle( "messages", LocaleContextHolder.getLocale());
		if(!StringUtil.isEmpty(userName) && !StringUtil.isEmpty(password)){
			AuthorizedUserInfo authenticationInfo = authorizedUserTokenService.doLogin(userName, password);
			if(authenticationInfo != null){
				session.setAttribute("aui", authenticationInfo);
				session.setAttribute("token", authenticationInfo.getToken());
				return new ModelAndView("redirect:/news", "aui", authenticationInfo);
			}else{
				return new ModelAndView("login", "error", bundle.getString("login.error"));
			}
		}else{
			return new ModelAndView("login", "error", bundle.getString("login.error"));
		}
	}
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		authorizedUserTokenService.logoutAuthorizedUserInfo((String) request.getSession().getAttribute("token"));
		request.getSession().invalidate();
		return new ModelAndView("login", "aui", null);
	}
}
