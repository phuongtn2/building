package com.controller;

import com.building.dto.AuthorizedUserInfo;
import com.building.dto.BuildingDto;
import com.building.dto.FloorDto;
import com.building.dto.RoomDto;
import com.building.services.ManagerBuildingService;
import com.dropbox.core.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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

@Controller
@RequestMapping("/history")
public class UserRoomHistoryController {
	@Autowired
	private ManagerBuildingService managerBuildingService;
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
		return "userRoomHistory/view";
	}

	@ModelAttribute("buildingDtoList")
	public List<BuildingDto> populateBuildingList() throws ServerException {
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
//			return "userRoomHistory/view";
//		} else {
//			status.setComplete();
//			//form success
//			return "userRoomHistory/view";
//		}
//	}


	// Floor
	@RequestMapping(value = "/floor/{id}", method = RequestMethod.GET)
	public String getListFloor(@PathVariable long id, Model model, HttpServletRequest request)  throws ServerException{
		AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
		List<FloorDto> listFloor = managerBuildingService.findAllFloorByBuildingId(id);
		model.addAttribute("floorDtoList",listFloor);
		return "userRoomHistory/floor/view";
	}

	// Room
	@RequestMapping(value = "/floor/room/{id}", method = RequestMethod.GET)
	public String getListRoom(@PathVariable long id, Model model, HttpServletRequest request)  throws ServerException{
		List<RoomDto> listRoom = managerBuildingService.findAllRoomByFloorId(id);
		model.addAttribute("roomDtoList",listRoom);
		return "userRoomHistory/room/view";
	}

	// Room history
	@RequestMapping(value = "/floor/room/detail{id}", method = RequestMethod.GET)
	public String getRoomHistory(@PathVariable long id, Model model, HttpServletRequest request)  throws ServerException{
		List<RoomDto> listRoom = managerBuildingService.findAllRoomByFloorId(id);
		model.addAttribute("roomDtoList",listRoom);
		return "userRoomHistory/room/view";
	}


}
