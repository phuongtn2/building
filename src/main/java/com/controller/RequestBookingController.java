package com.controller;

import com.building.dto.BookingServiceDto;
import com.building.services.FcmService;
import com.building.services.RequestBookingService;
import com.dropbox.core.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/request_booking")
public class RequestBookingController {
	@Autowired
	private RequestBookingService requestBookingService;
	@Autowired
	private FcmService fcmService;
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model) throws ServerException {
//		BookServiceDto bookServiceDto = new BookServiceDto();
		//command object
//		model.addAttribute("bookServiceDto", bookServiceDto);

		return "request_booking";
	}

	@RequestMapping(value = "/list/self",method = RequestMethod.GET, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<BookingServiceDto> findAllBooking(HttpServletRequest request) throws ServerException {
		List<BookingServiceDto> bookingServiceDtos = requestBookingService.findAllBooking();
		List<Integer> bookingUserIds = new ArrayList<Integer>();
		String content = "";
		for (BookingServiceDto bookingServiceDto: bookingServiceDtos) {
			bookingUserIds.add(bookingServiceDto.getUserId());
			content = content + bookingServiceDto.getMemo();
		}

		fcmService.sendNotification(bookingServiceDtos.get(0), bookingUserIds, content);
		return bookingServiceDtos;
	}
//	@ModelAttribute("bookServiceDtoList")
//	public List<BookServiceDto> populateBookServiceList() throws ServerException {
//		return requestBookingService.findAllBook();
//	}
//	@RequestMapping(method = RequestMethod.POST)
//	public String processSubmit(
//			@ModelAttribute("bookServiceDto") BookServiceDto bookServiceDto,
//			BindingResult result, SessionStatus status) {
//		//customerValidator.validate(customer, result);
//		if (result.hasErrors()) {
//			//if validator failed
//			return "request_booking";
//		} else {
//			status.setComplete();
//			//form success
//			return "request_booking";
//		}
//	}
//	@RequestMapping(method = RequestMethod.POST, params = "add")
//	public String addBook(@ModelAttribute("bookServiceDto") BookServiceDto bookServiceDto, HttpServletRequest request) throws ServerException {
//		AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
//		bookServiceDto.setUserId(aui.getUserId());
//		bookServiceDto.setFollowStatus((byte) 0);
//		requestBookingService.insertBook(bookServiceDto);
//		return "redirect:/request_booking";
//	}
//
//	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
//	public String getEdit(@PathVariable long id, Model model, HttpServletRequest request)  throws ServerException{
//		AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
//		BookServiceDto bookServiceDto = requestBookingService.findById(id);
//		bookServiceDto.setUpdateId(aui.getUserId());
//		model.addAttribute("bookServiceDto",bookServiceDto);
//		return "request_booking";
//	}
//	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
//	public String saveEdit(@ModelAttribute("bookServiceDto") BookServiceDto bookServiceDto, @PathVariable long id) throws ServerException {
//		requestBookingService.updateBook(bookServiceDto);
//		return "redirect:/request_booking";
//	}
//
//	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
//	public String delete(@PathVariable long id, Model model, HttpServletRequest request)  throws ServerException{
//		requestBookingService.deleteById(id);
//		return "redirect:/request_booking";
//	}

}
