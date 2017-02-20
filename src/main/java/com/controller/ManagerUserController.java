package com.controller;

import com.building.dto.*;
import com.building.services.ManagerBuildingService;
import com.building.services.ManagerUserService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Giang.DaoTu on 11/11/2016.
 */

@Controller
@RequestMapping("/user")
public class ManagerUserController {
    @Autowired
    private ManagerUserService managerUserService;
    @Autowired
    private ManagerBuildingService managerBuildingService;
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(method = RequestMethod.GET)
    public String initForm(ModelMap model){
         UserRoleRoomDto userRoleRoomDto = new UserRoleRoomDto();
        //command object
        model.addAttribute("userRoleRoomDto", userRoleRoomDto);
        //return form view
        return "user";
    }

    @ModelAttribute("userDtoList")
    public List<UserDto> populateUserList() throws ServerException {
        return managerUserService.findAllUser();
    }
    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(
            @ModelAttribute("userRoleRoomDto") UserRoleRoomDto userRoleRoomDto,
            BindingResult result, SessionStatus status) {

        //customerValidator.validate(customer, result);

        if (result.hasErrors()) {
            //if validator failed
            return "user";
        } else {
            status.setComplete();
            //form success
            return "user";
        }
    }

    @RequestMapping(method = RequestMethod.POST, params = "add")
    public String addUser(@ModelAttribute("userRoleRoomDto") UserRoleRoomDto userRoleRoomDto, HttpServletRequest request) throws ServerException {
        AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
        //get user
        UserDto userDto = userRoleRoomDto.getUserDto();
        userDto.setCreateId(aui.getUserId());
        userDto.setUpdateId(aui.getUserId());
        userDto.setPassword("123456");
        managerUserService.insertUser(userDto);
        //get role
        UserRoleGroupDto userRoleGroupDto = userRoleRoomDto.getUserRoleGroupDto();
        userRoleGroupDto.setUserId(userDto.getUserId());
        userRoleGroupDto.setCreateId(aui.getUserId());
        userRoleGroupDto.setUpdateId(aui.getUserId());
        managerUserService.insertUserRoleGroup(userRoleGroupDto);
//        //get user room
//        UserRoomDto userRoomDto = userRoleRoomDto.getUserRoomDto();
//        userRoomDto.setUserId(userDto.getUserId());
//        managerUserService.insertUserRoom(userRoomDto);
        return "redirect:/user";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String getEdit(@PathVariable long id, Model model, HttpServletRequest request)  throws ServerException{
        AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
        UserRoleRoomDto userRoleRoomDto = new UserRoleRoomDto();
        UserDto userDto = managerUserService.findUserById(id);
        userDto.setUpdateId(aui.getUserId());
        userRoleRoomDto.setUserDto(userDto);

        UserRoleGroupDto userRoleGroupDto = managerUserService.findUserRoleGroupByUserId(id);
        userRoleGroupDto.setUpdateId(aui.getUserId());
        userRoleRoomDto.setUserRoleGroupDto(userRoleGroupDto);
//        userRoleRoomDto.setUserRoomDto(managerUserService.findUserRoomById(id));
        model.addAttribute("userRoleRoomDto",userRoleRoomDto);
        return "user";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String saveEdit(@ModelAttribute("userRoleRoomDto") UserRoleRoomDto userRoleRoomDto, @PathVariable long id, HttpServletRequest request) throws ServerException {
        AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
//        //update user room
//        managerUserService.updateUserRoom(userRoleRoomDto.getUserRoomDto());

        //update user
        UserDto userDto = userRoleRoomDto.getUserDto();
        userDto.setUpdateId(aui.getUserId());
        managerUserService.updateUser(userDto);
        //update user role
        UserRoleGroupDto userRoleGroupDto = userRoleRoomDto.getUserRoleGroupDto();
        userRoleGroupDto.setUpdateId(aui.getUserId());
        managerUserService.updateUserRoleGroup(userRoleGroupDto);

        return "redirect:/user";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable long id, Model model, HttpServletRequest request)  throws ServerException{
        //delete user room
        managerUserService.deleteUserRoomByUserId(id);
        //delete user role
        managerUserService.deleteUserRoleGroupByUserId(id);
        //delete user
        managerUserService.deleteUserById(id);
        return "redirect:/user";
    }


    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String viewUserRoleRoomDetail(@PathVariable long id, Model model, HttpServletRequest request) throws ServerException{
        model.addAttribute("userDto",managerUserService.findUserById(id));
        model.addAttribute("userRoleGroupDto",managerUserService.findUserRoleGroupById(id));
        model.addAttribute("userRoomDto",managerUserService.findUserRoomByUserId(id));
        return "user_detail";
    }

    //find building
    @ModelAttribute("buildingDtoList")
    public List<BuildingDto> populateBuildingList() throws ServerException {
        return managerBuildingService.findAll();
    }

    //find Floor by buildingCode
    @RequestMapping(value = "/findFlorByBuildingCode/{id}", method = RequestMethod.GET, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<FloorDto> findFlorByBuildingCode(@PathVariable long id)  throws ServerException{
        return managerBuildingService.findAllFloorByBuildingId(id);
    }

}