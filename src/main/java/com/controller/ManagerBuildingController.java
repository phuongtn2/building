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
@RequestMapping("/building")
public class ManagerBuildingController {
	@Autowired
	private ManagerBuildingService managerBuildingService;
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model){
		BuildingDto buildingDto = new BuildingDto();
		//command object
		model.addAttribute("buildingDto", buildingDto);
		//return form view
		return "building/view";
	}

	@ModelAttribute("buildingDtoList")
	public List<BuildingDto> populateBuildingList() throws ServerException {
		return managerBuildingService.findAll();
	}
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(
			@ModelAttribute("buildingDto") BuildingDto buildingDto,
			BindingResult result, SessionStatus status) {

		//customerValidator.validate(customer, result);

		if (result.hasErrors()) {
			//if validator failed
			return "building/view";
		} else {
			status.setComplete();
			//form success
			return "building/view";
		}
	}

	@RequestMapping(method = RequestMethod.POST, params = "add")
	public String addBuilding(@ModelAttribute("buildingDto") BuildingDto buildingDto) throws ServerException {
		managerBuildingService.insertBuilding(buildingDto);
		return "redirect:/building";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String getEdit(@PathVariable long id, Model model, HttpServletRequest request)  throws ServerException{
		AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
		BuildingDto buildingDto = managerBuildingService.findById(id);
		buildingDto.setUpdateId(aui.getUserId());
		model.addAttribute("buildingDto",buildingDto);
		return "building/view";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String saveEdit(@ModelAttribute("buildingDto") BuildingDto buildingDto, @PathVariable long id) throws ServerException {
		managerBuildingService.update(buildingDto);
		return "redirect:/building";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable long id, Model model, HttpServletRequest request)  throws ServerException{
		List<Long> listFloorId = managerBuildingService.findAllFloorIdByBuildingId(id);
		//delete room
		managerBuildingService.deleteRoomByFloorId(listFloorId);
		//delete floor
		managerBuildingService.deleteFloorByBuildingId(id);
		//delete building
		managerBuildingService.deleteById(id);
		return "redirect:/building";
	}

	// Floor
	@RequestMapping(value = "/floor/{id}", method = RequestMethod.GET)
	public String getListFloor(@PathVariable long id, Model model, HttpServletRequest request)  throws ServerException{
		List<FloorDto> listFloor = managerBuildingService.findAllFloorByBuildingId(id);
		model.addAttribute("floorDtoList",listFloor);
		return "building/floor/view";
	}

	@RequestMapping(value = "/floor/{id}", method = RequestMethod.POST, params = "add")
	public String addFloor(@PathVariable long id, @ModelAttribute("floorDto") FloorDto floorDto, HttpServletRequest request) throws ServerException {
		AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
		floorDto.setCreateId(aui.getUserId());
		floorDto.setUpdateId(aui.getUserId());
		floorDto.setBuildingCode(id);
		managerBuildingService.insertFloor(floorDto);
		return "redirect:/building/floor/" + id;
	}
	@RequestMapping(value = "/floor/{buildingId}/edit/{id}", method = RequestMethod.GET)
	public String getEditFloor(@PathVariable long buildingId, @PathVariable long id, Model model, HttpServletRequest request)  throws ServerException{
		AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
		FloorDto floorDto = managerBuildingService.findFloorById(id);
		floorDto.setUpdateId(aui.getUserId());
		model.addAttribute("floorDto",floorDto);
		List<FloorDto> listFloor = managerBuildingService.findAllFloorByBuildingId(buildingId);
		model.addAttribute("floorDtoList",listFloor);
		return "building/floor/view";
	}

	@RequestMapping(value = "/floor/{buildingId}/edit/{id}", method = RequestMethod.POST)
	public String saveEditFloor(@ModelAttribute("floorDto") FloorDto floorDto, @PathVariable long buildingId, @PathVariable long id) throws ServerException {
		managerBuildingService.updateFloor(floorDto);
		return "redirect:/building/floor/"+ buildingId;
	}

	@RequestMapping(value = "/floor/{buildingId}/delete/{id}", method = RequestMethod.GET)
	public String deleteFloor(@PathVariable long buildingId,@PathVariable long id, Model model, HttpServletRequest request)  throws ServerException{
		//delete room
		List<Long> listId = new ArrayList<Long>();
		listId.add(id);
		managerBuildingService.deleteRoomByFloorId(listId);
		managerBuildingService.deleteFloorById(id);
		return "redirect:/building/floor/"+buildingId;
	}


	// Room
	@RequestMapping(value = "/floor/room/{id}", method = RequestMethod.GET)
	public String getListRoom(@PathVariable long id, Model model, HttpServletRequest request)  throws ServerException{
		List<RoomDto> listRoom = managerBuildingService.findAllRoomByFloorId(id);
		model.addAttribute("roomDtoList",listRoom);
		return "building/room/view";
	}

	@RequestMapping(value = "/floor/room/{id}", method = RequestMethod.POST, params = "add")
	public String addRoom(@PathVariable long id, @ModelAttribute("roomDto") RoomDto roomDto, HttpServletRequest request) throws ServerException {
		AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
		roomDto.setCreateId(aui.getUserId());
		roomDto.setUpdateId(aui.getUserId());
		roomDto.setFloorCode(id);
		managerBuildingService.insertRoom(roomDto);
		return "redirect:/building/floor/room/" + id;
	}
	@RequestMapping(value = "/floor/room/{floorId}/edit/{id}", method = RequestMethod.GET)
	public String getEditRoom(@PathVariable long floorId,@PathVariable long id, Model model, HttpServletRequest request)  throws ServerException{
		AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
		RoomDto roomDto = managerBuildingService.findRoomById(id);
		roomDto.setUpdateId(aui.getUserId());
		model.addAttribute("roomDto",roomDto);
		List<RoomDto> listRoom = managerBuildingService.findAllRoomByFloorId(floorId);
		model.addAttribute("roomDtoList",listRoom);
		return "building/room/view";
	}

	@RequestMapping(value = "/floor/room/{floorId}/edit/{id}", method = RequestMethod.POST)
	public String saveEditRoom(@ModelAttribute("roomDto") RoomDto roomDto, @PathVariable long floorId, @PathVariable long id) throws ServerException {
		managerBuildingService.updateRoom(roomDto);
		return "redirect:/building/floor/room/"+floorId;
	}

	@RequestMapping(value = "/floor/room/{floorId}/delete/{id}", method = RequestMethod.GET)
	public String deleteRoomById(@PathVariable long floorId,@PathVariable long id, Model model, HttpServletRequest request)  throws ServerException{
		managerBuildingService.deleteRoomById(id);
		return "redirect:/building/floor/room/"+floorId;
	}


}
