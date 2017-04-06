package com.controller;

import com.building.dto.login.AuthorizedUserInfo;
import com.building.dto.master.MasterBuildingDto;
import com.building.dto.master.MasterFloorDto;
import com.building.dto.master.MasterRoomDto;
import com.building.services.BuildingService;
import com.building.services.error.ServiceException;
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
import javax.ws.rs.QueryParam;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/building")
public class BuildingController {
	@Autowired
	private BuildingService buildingService;
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model){
		MasterBuildingDto buildingDto = new MasterBuildingDto();
		//command object
		model.addAttribute("buildingDto", buildingDto);
		//return form view
		return "building";
	}

	@ModelAttribute("buildingDtoList")
	public List<MasterBuildingDto> populateBuildingList() throws ServiceException {
		return buildingService.findAll();
	}
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(
			@ModelAttribute("buildingDto") MasterBuildingDto buildingDto,
			BindingResult result, SessionStatus status) {

		//customerValidator.validate(customer, result);

		if (result.hasErrors()) {
			//if validator failed
			return "building";
		} else {
			status.setComplete();
			//form success
			return "building";
		}
	}

	@RequestMapping(method = RequestMethod.POST, params = "add")
	public String addBuilding(@ModelAttribute("buildingDto") MasterBuildingDto buildingDto) throws ServiceException {
		AuthorizedUserInfo aui = new AuthorizedUserInfo();
		buildingDto.setCreateId(aui.getUserId());
		buildingDto.setUpdateId(aui.getUserId());
		buildingService.insertBuilding(buildingDto);
		return "redirect:/building";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String getEdit(@PathVariable long id, Model model, HttpServletRequest request)  throws ServiceException {
		AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
		MasterBuildingDto buildingDto = buildingService.findById(id);
		buildingDto.setUpdateId(aui.getUserId());
		model.addAttribute("buildingDto",buildingDto);
		return "building";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String saveEdit(@ModelAttribute("buildingDto") MasterBuildingDto buildingDto, @PathVariable long id) throws ServiceException {
		buildingService.update(buildingDto);
		return "redirect:/building";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable long id, Model model, HttpServletRequest request)  throws ServiceException{
		List<Long> listFloorId = buildingService.findAllFloorIdByBuildingId(id);
		//delete room
		buildingService.deleteRoomByFloorId(listFloorId);
		//delete floor
		buildingService.deleteFloorByBuildingId(id);
		//delete building
		buildingService.deleteById(id);
		return "redirect:/building";
	}

	// Floor
	@RequestMapping(value = "/floor/{id}", method = RequestMethod.GET)
	public String getListFloor(@PathVariable long id, Model model, HttpServletRequest request)  throws ServiceException{
		List<MasterFloorDto> listFloor = buildingService.findAllFloorByBuildingId(id);
		model.addAttribute("floorDtoList",listFloor);
		return "building/floor/view";
	}

	@RequestMapping(value = "/floor/{id}", method = RequestMethod.POST, params = "add")
	public String addFloor(@PathVariable long id, @ModelAttribute("floorDto") MasterFloorDto floorDto, HttpServletRequest request) throws ServiceException {
		AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
		floorDto.setCreateId(aui.getUserId());
		floorDto.setUpdateId(aui.getUserId());
		floorDto.setBuildingCode(id);
		buildingService.insertFloor(floorDto);
		return "redirect:/building/floor/" + id;
	}
	@RequestMapping(value = "/floor/{buildingId}/edit/{id}", method = RequestMethod.GET)
	public String getEditFloor(@PathVariable long buildingId, @PathVariable long id, Model model, HttpServletRequest request)  throws ServiceException{
		AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
		MasterFloorDto floorDto = buildingService.findFloorById(id);
		floorDto.setUpdateId(aui.getUserId());
		model.addAttribute("floorDto",floorDto);
		List<MasterFloorDto> listFloor = buildingService.findAllFloorByBuildingId(buildingId);
		model.addAttribute("floorDtoList",listFloor);
		return "building/floor/view";
	}

	@RequestMapping(value = "/floor/{buildingId}/edit/{id}", method = RequestMethod.POST)
	public String saveEditFloor(@ModelAttribute("floorDto") MasterFloorDto floorDto, @PathVariable long buildingId, @PathVariable long id) throws ServiceException {
		buildingService.updateFloor(floorDto);
		return "redirect:/building/floor/"+ buildingId;
	}

	@RequestMapping(value = "/floor/{buildingId}/delete/{id}", method = RequestMethod.GET)
	public String deleteFloor(@PathVariable long buildingId,@PathVariable long id, Model model, HttpServletRequest request)  throws ServiceException{
		//delete room
		List<Long> listId = new ArrayList<Long>();
		listId.add(id);
		buildingService.deleteRoomByFloorId(listId);
		buildingService.deleteFloorById(id);
		return "redirect:/building/floor/"+buildingId;
	}


	// Room
	@RequestMapping(value = "/floor/room/{id}", method = RequestMethod.GET)
	public String getListRoom(@PathVariable long id, Model model, HttpServletRequest request)  throws ServiceException{
		List<MasterRoomDto> listRoom = buildingService.findAllRoomByFloorId(id);
		model.addAttribute("roomDtoList",listRoom);
		return "building/room/view";
	}

	@RequestMapping(value = "/floor/room/{id}", method = RequestMethod.POST, params = "add")
	public String addRoom(@PathVariable long id, @ModelAttribute("roomDto") MasterRoomDto roomDto, HttpServletRequest request) throws ServiceException {
		AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
		roomDto.setCreateId(aui.getUserId());
		roomDto.setUpdateId(aui.getUserId());
		roomDto.setFloorCode(id);
		buildingService.insertRoom(roomDto);
		return "redirect:/building/floor/room/" + id;
	}
	@RequestMapping(value = "/floor/room/{floorId}/edit/{id}", method = RequestMethod.GET)
	public String getEditRoom(@PathVariable long floorId,@PathVariable long id, Model model, HttpServletRequest request)  throws ServiceException{
		AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
		MasterRoomDto roomDto = buildingService.findRoomById(id);
		roomDto.setUpdateId(aui.getUserId());
		model.addAttribute("roomDto",roomDto);
		List<MasterRoomDto> listRoom = buildingService.findAllRoomByFloorId(floorId);
		model.addAttribute("roomDtoList",listRoom);
		return "building/room/view";
	}

	@RequestMapping(value = "/floor/room/{floorId}/edit/{id}", method = RequestMethod.POST)
	public String saveEditRoom(@ModelAttribute("roomDto") MasterRoomDto roomDto, @PathVariable long floorId, @PathVariable long id) throws ServiceException {
		buildingService.updateRoom(roomDto);
		return "redirect:/building/floor/room/"+floorId;
	}

	@RequestMapping(value = "/floor/room/{floorId}/delete/{id}", method = RequestMethod.GET)
	public String deleteRoomById(@PathVariable long floorId,@PathVariable long id, Model model, HttpServletRequest request)  throws ServiceException{
		buildingService.deleteRoomById(id);
		return "redirect:/building/floor/room/"+floorId;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<MasterBuildingDto> getListBuilding(HttpServletRequest request,@QueryParam("buildingName") String buildingName) throws ServiceException {
		return buildingService.findByBuildingName(buildingName);
	}

}
