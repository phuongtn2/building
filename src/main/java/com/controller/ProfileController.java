package com.controller;

import com.building.dto.ProfileDto;
import com.building.dto.UserRoleRoomDto;
import com.building.dto.UserRoomDto;
import com.building.dto.login.AuthorizedUserInfo;
import com.building.dto.login.UserDto;
import com.building.dto.login.UserRoleGroupDto;
import com.building.dto.master.MasterBuildingDto;
import com.building.dto.master.MasterFloorDto;
import com.building.services.BuildingService;
import com.building.services.UserService;
import com.dropbox.core.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Giang.DaoTu on 11/11/2016.
 */

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private UserService managerUserService;
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(method = RequestMethod.GET)
    public String initForm(ModelMap model){
        return "profile";
    }

    @ModelAttribute("profileDto")
    public ProfileDto populateUserList(HttpServletRequest request) throws ServerException {
        AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
        ProfileDto profileDto = new ProfileDto();
        profileDto.setUserDto(managerUserService.findUserById(aui.getUserId()));

//        profileDto.roomAlias(
//                managerUserRoom.getRoomCode(managerUserService.findUserRoomById(aui.getUserId()));
//        profileDto.floorAlias(managerUserService.fin(aui.getUserId()));
//        profileDto.buildingName(managerUserService.findUserById(aui.getUserId()));
        return profileDto;
    }

}