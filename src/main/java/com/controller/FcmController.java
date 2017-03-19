package com.controller;

import com.building.dto.login.AuthorizedUserInfo;
import com.building.services.FcmService;
import com.building.services.UserService;
import com.building.services.error.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created by phuongtn on 12/28/2016.
 */

@Controller
public class FcmController {
    @Autowired
    private FcmService fcmService;
    @RequestMapping(value = "/fcm/store-token", method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED, produces = MediaType.APPLICATION_JSON)
    int storeToken(@Context HttpServletRequest request, @FormParam("token") String token) throws ServiceException{
        AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
        return fcmService.storeToken(aui, token);
    }

    @RequestMapping(value = "/remove-token", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED, produces = MediaType.APPLICATION_JSON)
    int removeToken(@Context HttpServletRequest request, @FormParam("token") String token) throws ServiceException{
        AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
        return fcmService.removeToken(aui, token);
    }
}
