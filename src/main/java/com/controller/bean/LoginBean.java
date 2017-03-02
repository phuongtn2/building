package com.controller.bean;


import com.building.dto.login.AuthorizedUserInfo;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginBean implements Filter{
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		AuthorizedUserInfo aui = (AuthorizedUserInfo) session.getAttribute("aui");
		if (aui == null && !req.getRequestURI().contains("/login")){
			RequestDispatcher rd;
			if(req.getRequestURI().contains("/forgotpassword"))
				rd = req.getRequestDispatcher("/forgotpassword");
			else
				rd = req.getRequestDispatcher("/");
			rd.forward(servletRequest, servletResponse);
			return;
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {

	}
}
