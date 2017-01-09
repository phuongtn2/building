package com.controller;

import com.building.dto.AuthorizedUserInfo;
import com.building.dto.MasterServicesDto;
import com.building.services.ManagerMasterServicesService;
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

/**
 * Created by Giang.DaoTu on 11/15/2016.
 */
@Controller
@RequestMapping("/service")
public class ManagerMasterServicesController {

        @Autowired
        private ManagerMasterServicesService managerMasterServicesService;
        @InitBinder
        public void initBinder(WebDataBinder binder) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        }

        @RequestMapping(method = RequestMethod.GET)
        public String initForm(ModelMap model){
            MasterServicesDto masterServicesDto = new MasterServicesDto();
            //command object
            model.addAttribute("masterServiceDto", masterServicesDto);
            //return form view
            return "service/view";
        }

        @ModelAttribute("masterServicesDtoList")
        public List<MasterServicesDto> populateMasterServicesList() throws ServerException {
            return managerMasterServicesService.findAll();
        }
        @RequestMapping(method = RequestMethod.POST)
        public String processSubmit(
                @ModelAttribute("masterServiceDto") MasterServicesDto masterServicesDto,
                BindingResult result, SessionStatus status) {

            //customerValidator.validate(customer, result);

            if (result.hasErrors()) {
                //if validator failed
                return "service/view";
            } else {
                status.setComplete();
                //form success
                return "service/view";
            }
        }

        @RequestMapping(method = RequestMethod.POST, params = "add")
        public String addMasterServices(@ModelAttribute("masterServiceDto") MasterServicesDto masterServicesDto) throws ServerException {
            managerMasterServicesService.insertMasterServices(masterServicesDto);
            return "redirect:/service";
        }

        @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
        public String getEdit(@PathVariable long id, Model model, HttpServletRequest request)  throws ServerException{
            AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
            MasterServicesDto masterServicesDto = managerMasterServicesService.findById(id);
            masterServicesDto.setUpdateId(aui.getUserId());
            model.addAttribute("masterServiceDto",masterServicesDto);
            return "service/view";
        }

        @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
        public String saveEdit(@ModelAttribute("masterServiceDto") MasterServicesDto masterServicesDto, @PathVariable long id) throws ServerException {
            managerMasterServicesService.update(masterServicesDto);
            return "redirect:/service";
        }

        @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
        public String delete(@PathVariable long id, Model model, HttpServletRequest request)  throws ServerException{
            managerMasterServicesService.deleteById(id);
            return "redirect:/service";
        }

}
