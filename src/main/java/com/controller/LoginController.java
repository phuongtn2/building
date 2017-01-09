package com.controller;

import com.building.dto.AuthorizedUserInfo;
import com.building.services.AuthorizedUserTokenService;
import com.building.services.error.ServiceException;
import com.building.util.str.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
	@Autowired
	private AuthorizedUserTokenService authorizedUserTokenService;

	@RequestMapping("/login")
	public ModelAndView login(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		String message;
		if(!StringUtil.isEmpty(userName) && !StringUtil.isEmpty(password)){
			AuthorizedUserInfo authenticationInfo = authorizedUserTokenService.doLogin(userName, password);
			if(authenticationInfo != null){
				session.setAttribute("aui", authenticationInfo);
				session.setAttribute("token", authenticationInfo.getToken());
				return new ModelAndView("redirect:/news", "aui", authenticationInfo);
			}else{
				message = "Chưa có tài khoản.";
				return new ModelAndView("login", "error", message);
			}
		}else{
			message = "Sai tên đăng nhập/mật khẩu.";
			return new ModelAndView("login", "error", message);
		}
	}
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		authorizedUserTokenService.logoutAuthorizedUserInfo((String) request.getSession().getAttribute("token"));
		request.getSession().invalidate();
		return new ModelAndView("login", "aui", null);
	}
}
