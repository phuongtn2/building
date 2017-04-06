package com.controller;

import com.building.dto.*;
import com.building.dto.login.AuthorizedUserInfo;
import com.building.dto.master.MasterComplaintDto;
import com.building.dto.master.MasterServicesDto;
import com.building.services.ComplaintService;
import com.building.services.ServicesService;
import com.building.services.Role;
import com.building.services.error.ServiceException;
import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/complaint")
public class ComplaintController {
	@Autowired
	private ComplaintService complaintService;
	@Autowired
	private ServicesService managerMasterServicesService;
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model) throws ServiceException {
		MasterComplaintDto complaintDto = new MasterComplaintDto();
		//command object
		model.addAttribute("complaintDto", complaintDto);
		//model.addAttribute("newsDtoList", newsService.findAll());
		//return form view
		return "complaint";
	}

	@ModelAttribute("tmComplaintList")
	public List<TMComplaintDto> populateTMComplaintList() throws ServiceException {
		List<TMComplaintDto> tmComplaintList = new ArrayList<TMComplaintDto>();
		List<MasterComplaintDto> listComplaint = complaintService.findAllComplaint();
		List<Long> complaintCodeList = new ArrayList<Long>();
		if (listComplaint.size() > 0){
			for (MasterComplaintDto complaintDto : listComplaint) {
				complaintCodeList.add(complaintDto.getComplaintCode());
			}
			//Find TComplaint
			List<TransferComplaintDto> tComplaintList = complaintService.findAllTComplaint(complaintCodeList);
			List<Long> parentComplaintCodeList = new ArrayList<Long>();
			for (TransferComplaintDto transferComplaintDto : tComplaintList) {
				parentComplaintCodeList.add(transferComplaintDto.getParentComplaintCode());
			}
			List<TransferReplyDto> tReply = complaintService.findAllTReply(parentComplaintCodeList);
			for (MasterComplaintDto complaintDto : listComplaint) {
				TMComplaintDto tmComplaintDto = new TMComplaintDto();
				List<TransferComplaintDto> tComplaintListTemp = new ArrayList<TransferComplaintDto>();
				for (TransferComplaintDto transferComplaintDto: tComplaintList) {
					if(transferComplaintDto.getComplaintCode() == complaintDto.getComplaintCode()){
						List<TransferReplyDto> tReplyTemp = new ArrayList<TransferReplyDto>();
						for (TransferReplyDto transferReplyDto: tReply) {
							if(transferReplyDto.getParentComplaintCode() == transferComplaintDto.getParentComplaintCode()){
								tReplyTemp.add(transferReplyDto);
							}
						}
						transferComplaintDto.settReplyDtoList(tReplyTemp);
						tComplaintListTemp.add(transferComplaintDto);
					}
				}
				tmComplaintDto.setmComplaint(complaintDto);
				tmComplaintDto.settComplaintList(tComplaintListTemp);
				tmComplaintList.add(tmComplaintDto);
			}
		}
		return tmComplaintList;
	}
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(
			@ModelAttribute("complaintDto") MasterComplaintDto complaintDto,
			BindingResult result, SessionStatus status) {
		//customerValidator.validate(customer, result);
		if (result.hasErrors()) {
			//if validator failed
			return "complaint";
		} else {
			status.setComplete();
			//form success
			return "complaint";
		}
	}
	@RequestMapping(method = RequestMethod.POST, params = "add")
	public String addComplaint(@ModelAttribute("complaintDto") MasterComplaintDto complaintDto, HttpServletRequest request) throws ServiceException {
		AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
		complaintDto.setCreateId(aui.getUserId());
		complaintService.insertComplaint(complaintDto);
		return "redirect:/complaint";
	}

	@RequestMapping(value = "/comment",method = RequestMethod.POST, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public TransferComplaintDto addTComplaint( HttpServletRequest request) throws ServiceException, JSONException {
		try {
			String inputJson = IOUtils.toString(request.getReader());
			JSONObject jsonObj = new JSONObject(inputJson);
			TransferComplaintDto transferComplaintDto = new TransferComplaintDto();
			transferComplaintDto.setComplaintCode(Long.parseLong(jsonObj.getString("complaintCode")));
			transferComplaintDto.setTranSeq(Integer.parseInt(jsonObj.getString("tranSeq")));
			transferComplaintDto.setMessage(jsonObj.getString("message"));
			transferComplaintDto.setParentComplaintCode(Long.parseLong(jsonObj.getString("parentComplaintCode")));
			AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
			transferComplaintDto.setUserId(aui.getUserId());
			transferComplaintDto.setUserName(aui.getFullName());
			complaintService.insertTComplaint(transferComplaintDto);
			return transferComplaintDto;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	@RequestMapping(method = RequestMethod.POST, params = "reply")
	public String addTReply(@ModelAttribute("complaintDto") MasterComplaintDto complaintDto) throws ServiceException {
		complaintService.insertComplaint(complaintDto);
		return "redirect:/complaint";
	}

	@RequestMapping(method = RequestMethod.POST, params = "follow")
	public String follow(@ModelAttribute("complaintDto") MasterComplaintDto complaintDto) throws ServiceException {
		if(complaintDto.getFollowStatus() == 0){
			complaintDto.setFollowStatus((byte) 1);
		}else{
			complaintDto.setFollowStatus((byte) 0);
		}
		complaintService.updateFollowStatus(complaintDto);
		return "redirect:/complaint";
		//return "complaint/view";
	}

	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public String listComplaintHistory(Model model, HttpServletRequest request) throws ServiceException {
		AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
		boolean per = aui.getRoleSet().contains(Role.ADMIN);
		List<MasterComplaintDto> listComplaintHistory = complaintService.findAllComplaintHistory(aui,per);
		/*if(aui.getRoleSet().contains(Role.ADMIN)){
			listComplaintHistory = complaintService.findAllComplaint();
		}else{
			listComplaintHistory = complaintService.findAllComplaintHistory(aui.getUserId());
		}*/

		model.addAttribute("listComplaintHistory", listComplaintHistory);
		return "complaint/view_history";
	}

	@ModelAttribute("serviceDtoList")
	public List<MasterServicesDto> populateServiceList() throws ServiceException {
		return managerMasterServicesService.findAll();
	}
}
