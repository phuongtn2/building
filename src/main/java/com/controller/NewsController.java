package com.controller;

import com.building.dto.NewsDetailDto;
import com.building.dto.NewsNewsDetailDto;
import com.building.dto.login.AuthorizedUserInfo;
import com.building.dto.master.MasterNewsDto;
import com.building.services.NewsService;
import com.building.services.error.ServiceException;
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
@RequestMapping("/news")
public class NewsController {
	@Autowired
	private NewsService newsService;
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model) throws ServiceException {
		NewsNewsDetailDto newsNewsDetailDto = new NewsNewsDetailDto();
		//command object
		model.addAttribute("newsNewsDetailDto", newsNewsDetailDto);
		//model.addAttribute("newsDtoList", newsService.findAll());
		//return form view
		return "news";
	}

	@ModelAttribute("newsNewsDetailDtoList")
	public List<NewsNewsDetailDto> populateNewsList() throws ServiceException {
		List<NewsNewsDetailDto> newsNewsDetailDtoList = new ArrayList<NewsNewsDetailDto>();
		List<MasterNewsDto> newsDtos = newsService.findAll();
		if(newsDtos != null){
			for (MasterNewsDto newsDto:newsDtos) {
				NewsNewsDetailDto newsNewsDetailDto = new NewsNewsDetailDto();
				List<NewsDetailDto> newsDetailDtos = newsService.findByNewsId(newsDto.getNewsCode());
				List<NewsDetailDto> newsDetailDtos1 = new ArrayList<NewsDetailDto>();
				for (NewsDetailDto newsDetailDto:newsDetailDtos) {
					MasterNewsDto newsDto1 = newsService.findById(newsDetailDto.getRefNewsCode());
					NewsDetailDto newsDetailDto1 = new NewsDetailDto();
					newsDetailDto1 = newsDetailDto;
					newsDetailDto1.setMasterNewsDto(newsDto1);
					newsDetailDtos1.add(newsDetailDto1);
				}
				newsNewsDetailDto.setMasterNewsDto(newsDto);
				newsNewsDetailDto.setNewsDetailDtoList(newsDetailDtos1);
				newsNewsDetailDtoList.add(newsNewsDetailDto);
			}
		}
		return  newsNewsDetailDtoList;
	}
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(
			@ModelAttribute("newsDto") MasterNewsDto newsDto,
			BindingResult result, SessionStatus status) {
		//customerValidator.validate(customer, result);
		if (result.hasErrors()) {
			//if validator failed
			return "news/view";
		} else {
			status.setComplete();
			//form success
			return "news/view";
		}
	}
	@RequestMapping(method = RequestMethod.POST, params = "add")
	public String addNews(@ModelAttribute("newsDto") MasterNewsDto newsDto) throws ServiceException {
		AuthorizedUserInfo aui = new AuthorizedUserInfo();
		newsDto.setCreateId(aui.getUserId());
		newsDto.setUpdateId(aui.getUserId());
		newsService.insertNews(newsDto);
		return "redirect:/news";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String getEdit(@PathVariable long id, Model model, HttpServletRequest request)  throws ServiceException{
		AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
		MasterNewsDto newsDto = newsService.findById(id);
		newsDto.setUpdateId(aui.getUserId());
		model.addAttribute("newsDto",newsDto);
		return "news/view";
	}
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String saveEdit(@ModelAttribute("newsDto") MasterNewsDto newsDto, @PathVariable long id) throws ServiceException {
		newsService.update(newsDto);
		return "redirect:/news";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable long id, Model model, HttpServletRequest request)  throws ServiceException{
		newsService.deleteById(id);
		return "redirect:/news";
	}

	@RequestMapping(value = "/{id}" , method = RequestMethod.GET)
	public String viewNewsDetail(@PathVariable long id, HttpServletRequest request, Model model) throws ServiceException{
		AuthorizedUserInfo aui = (AuthorizedUserInfo) request.getSession().getAttribute("aui");
		MasterNewsDto newsDto = newsService.findById(id);
		model.addAttribute("newsDto",newsDto);
		return "news/news_detail/view";
	}
}
