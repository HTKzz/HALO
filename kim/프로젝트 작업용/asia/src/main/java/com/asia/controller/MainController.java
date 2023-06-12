package com.asia.controller;

import java.util.List;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asia.dto.ApplicationDto;
import com.asia.dto.NoticeDto;
import com.asia.entity.Notice;
import com.asia.service.ApplicationService;
import com.asia.service.NoticeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final ApplicationService applicationService;
	private final NoticeService noticeService;
	
	@GetMapping(value = "/")
	public String main(Model model) {
		
		List<ApplicationDto> slideList = applicationService.getSlideList();
		List<NoticeDto> noticeList = noticeService.getNoticeList();
		
		model.addAttribute("slideList", slideList);
		model.addAttribute("noticeList", noticeList);
		
		return "main";
	}
	
	@GetMapping(value = "/useinfo")
	public String useinfo() {

		return "menu/useinfo";
	}
	
	@GetMapping(value = "/accIntro")
	public String accIntro() {

		return "menu/accIntro";
	}
	
	@GetMapping(value = "/wayMap")
	public String wayMap() {

		return "menu/wayMap";
	}

	// 권한이 없는 사람이 접근했을경우
	@RequestMapping(value = "/error_user")
	public String error() {
		return "member/error";
	}
	
	@RequestMapping(value = "/members/select")
	public String signupSelect() {
		return "member/select";
	}
}