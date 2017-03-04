package com.controller;

import com.building.dto.master.MasterAssetDto;
import com.building.dto.login.AuthorizedUserInfo;
import com.building.dto.master.MasterBuildingDto;
import com.building.services.AssetService;
import com.building.services.BuildingService;
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
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/asset")
public class AssetController {
	@Autowired
	private AssetService masterAssetService;
	@Autowired
	private BuildingService buildingService;
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model) throws ServerException {
		MasterAssetDto masterAssetDto = new MasterAssetDto();
		//command object
		model.addAttribute("MasterAssetDto", masterAssetDto);
		//return form view
		return "asset";
	}

	@ModelAttribute("buildingDtoDtoList")
	public List<MasterBuildingDto> populateMasterBuildingList() throws ServerException {
		return buildingService.findAll();
	}

	@ModelAttribute("masterAssetDtoList")
	public List<MasterAssetDto> populateMasterAssetList() throws ServerException {
		return masterAssetService.findAll();
	}
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(
			@ModelAttribute("masterAssetDto") MasterAssetDto masterAssetDto,
			BindingResult result, SessionStatus status) {
		//customerValidator.validate(customer, result);
		if (result.hasErrors()) {
			//if validator failed
			return "asset";
		} else {
			status.setComplete();
			//form success
			return "asset";
		}
	}
	@RequestMapping(method = RequestMethod.POST, params = "add")
	public String addAsset(@ModelAttribute("masterAssetDto") MasterAssetDto masterAssetDto) throws ServerException {
		AuthorizedUserInfo aui = new AuthorizedUserInfo();
		masterAssetDto.setCreateId(aui.getUserId());
		masterAssetDto.setUpdateId(aui.getUserId());
		masterAssetService.insertMasterAsset(masterAssetDto);
		return "redirect:/asset";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String getEdit(@PathVariable long id, Model model, HttpServletRequest request)  throws ServerException{
		AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
		MasterAssetDto masterAssetDto = masterAssetService.findById(id);
		masterAssetDto.setUpdateId(aui.getUserId());
		model.addAttribute("masterAssetDto",masterAssetDto);
		return "asset";
	}
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String saveEdit(@ModelAttribute("masterAssetDto") MasterAssetDto masterAssetDto, @PathVariable long id) throws ServerException {
		masterAssetService.update(masterAssetDto);
		return "redirect:/asset";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable long id, Model model, HttpServletRequest request)  throws ServerException{
		masterAssetService.deleteById(id);
		return "redirect:/asset";
	}
}
