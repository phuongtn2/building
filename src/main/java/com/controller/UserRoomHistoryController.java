package com.controller;

import com.building.dto.login.AuthorizedUserInfo;
import com.building.dto.master.MasterBuildingDto;
import com.building.dto.master.MasterFloorDto;
import com.building.dto.master.MasterRoomDto;
import com.building.services.BuildingService;
import com.building.services.error.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/history")
public class UserRoomHistoryController {
	@Autowired
	private BuildingService managerBuildingService;
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model){
//		BuildingDto buildingDto = new BuildingDto();
		//command object
//		model.addAttribute("buildingDto", buildingDto);
		//return form view
		return "userRoomHistory";
	}

	@ModelAttribute("buildingDtoList")
	public List<MasterBuildingDto> populateBuildingList() throws ServiceException {
		return managerBuildingService.findAll();
	}
//	@RequestMapping(method = RequestMethod.POST)
//	public String processSubmit(
//			@ModelAttribute("buildingDto") BuildingDto buildingDto,
//			BindingResult result, SessionStatus status) {
//
//		//customerValidator.validate(customer, result);
//
//		if (result.hasErrors()) {
//			//if validator failed
//			return "userRoomHistory";
//		} else {
//			status.setComplete();
//			//form success
//			return "userRoomHistory";
//		}
//	}


	// Floor
	@RequestMapping(value = "/floor/{id}", method = RequestMethod.GET)
	public String getListFloor(@PathVariable long id, Model model, HttpServletRequest request)  throws ServiceException{
		AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
		List<MasterFloorDto> listFloor = managerBuildingService.findAllFloorByBuildingId(id);
		model.addAttribute("floorDtoList",listFloor);
		return "userRoomHistory/floor/view";
	}
	@RequestMapping(value = "/findFloor/{id}", method = RequestMethod.GET)
	public List<MasterFloorDto> getListFloor_Add(@PathVariable long id, HttpServletRequest request)  throws ServiceException{
		AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");

		return managerBuildingService.findAllFloorByBuildingId(id);
	}

	// Room
	@RequestMapping(value = "/floor/room/{id}", method = RequestMethod.GET)
	public String getListRoom(@PathVariable long id, Model model, HttpServletRequest request)  throws ServiceException{
		List<MasterRoomDto> listRoom = managerBuildingService.findAllRoomByFloorId(id);
		model.addAttribute("roomDtoList",listRoom);
		return "userRoomHistory/room/view";
	}
	@RequestMapping(value = "/findRoom/{id}", method = RequestMethod.GET)
	public List<MasterRoomDto> getListRoom_Add(@PathVariable long id, HttpServletRequest request)  throws ServiceException{

		return managerBuildingService.findAllRoomByFloorId(id);
	}

	// Room history
	@RequestMapping(value = "/floor/room/detail{id}", method = RequestMethod.GET)
	public String getRoomHistory(@PathVariable long id, Model model, HttpServletRequest request)  throws ServiceException{
		List<MasterRoomDto> listRoom = managerBuildingService.findAllRoomByFloorId(id);
		model.addAttribute("roomDtoList",listRoom);
		return "userRoomHistory/room/view";
	}

}
