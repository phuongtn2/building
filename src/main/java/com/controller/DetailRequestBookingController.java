package com.controller;

import com.building.dto.BookingServiceDto;
import com.building.dto.DetailBookingServiceDto;
import com.building.dto.login.UserDto;
import com.building.services.RequestBookingService;

import com.building.services.UserService;
import com.dropbox.core.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/notifications")
public class DetailRequestBookingController {
	@Autowired
	private RequestBookingService requestBookingService;
	@Autowired
	private UserService managerUserService;
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model) throws ServerException {
		return "list_request_booking";
	}


	@ModelAttribute("ListBookingServiceDto")
	public List<DetailBookingServiceDto> populateListBookServiceList() throws ServerException {

		List<DetailBookingServiceDto> listBookingServiceDto = new ArrayList<DetailBookingServiceDto>();
		List<BookingServiceDto> bookingServiceDto = requestBookingService.findAllBooking();
		if (bookingServiceDto != null) {
			for (BookingServiceDto bookingServiceDto1 : bookingServiceDto) {
				DetailBookingServiceDto detailBookingServiceDto = new DetailBookingServiceDto();
				detailBookingServiceDto.setBookingServiceDto(bookingServiceDto1);
				List<String> stringList = requestBookingService.findNameByBookingServiceCode(bookingServiceDto1.getBookingServiceCode(), bookingServiceDto1.getOption());
				String serviceOrAsset = "";
				for (String s : stringList) {
					serviceOrAsset = serviceOrAsset + s + "<BR>";
				}
				detailBookingServiceDto.setServiceOrAssetName(serviceOrAsset);
				listBookingServiceDto.add(detailBookingServiceDto);
			}

		}
		return listBookingServiceDto;
	}

	@RequestMapping(value = "/NotifyListBookingServiceDto", method = RequestMethod.GET)
    @ResponseBody
	public List<BookingServiceDto> populateNotifyListBookServiceList( ) throws ServerException {
		List<BookingServiceDto> bookingServiceDto = requestBookingService.findAllNewBooking();
//        bookingServiceDto = requestBookingService.findAllNewBooking();
		return bookingServiceDto;

	}


	@ModelAttribute("serviceUserDtoList")
	public List<UserDto> populateUserList() throws ServerException {
		return managerUserService.findAllUser();
	}

}
