package com.controller;

import com.building.dto.BookingServiceDto;
import com.building.dto.DetailBookingServiceDto;
import com.building.services.RequestBookingService;
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

		return "list_request_booking";
	}

	@ModelAttribute("ListBookingServiceDto")
	public List<DetailBookingServiceDto> populateListBookServiceList() throws ServerException {
//		return requestBookingService.findAllBooking();
		List<DetailBookingServiceDto> listBookingServiceDto = new ArrayList<DetailBookingServiceDto>();
		List<BookingServiceDto> bookingServiceDto =  requestBookingService.findAllBooking();


		if(bookingServiceDto != null){
			for (BookingServiceDto bookingServiceDto1:bookingServiceDto) {
				DetailBookingServiceDto detailBookingServiceDto = new DetailBookingServiceDto();
//				List<String> serviceName = new ArrayList<String>();
//				List<String> assetName = new ArrayList<String>();
				detailBookingServiceDto.setBookingServiceDto(bookingServiceDto1);
				listBookingServiceDto.add(detailBookingServiceDto);
			}

		}

//		if(newsDtos != null){
//			for (MasterNewsDto newsDto:newsDtos) {
//				NewsNewsDetailDto newsNewsDetailDto = new NewsNewsDetailDto();
//				List<NewsDetailDto> newsDetailDtos = newsService.findByNewsId(newsDto.getNewsCode());
//				List<NewsDetailDto> newsDetailDtos1 = new ArrayList<NewsDetailDto>();
//				for (NewsDetailDto newsDetailDto:newsDetailDtos) {
//					MasterNewsDto newsDto1 = newsService.findById(newsDetailDto.getRefNewsCode());
//					NewsDetailDto newsDetailDto1 = new NewsDetailDto();
//					newsDetailDto1 = newsDetailDto;
//					newsDetailDto1.setMasterNewsDto(newsDto1);
//					newsDetailDtos1.add(newsDetailDto1);
//				}
//				newsNewsDetailDto.setMasterNewsDto(newsDto);
//				newsNewsDetailDto.setNewsDetailDtoList(newsDetailDtos1);
//				newsNewsDetailDtoList.add(newsNewsDetailDto);
//			}
//		}
		return listBookingServiceDto;
	}

}
