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
	
	// 메인페이지 호출
	@GetMapping(value = "/")
	public String main(Model model) {
		
		List<ApplicationDto> slideList = applicationService.getSlideList();
		List<NoticeDto> noticeList = noticeService.getNoticeList();
		
		model.addAttribute("slideList", slideList);
		model.addAttribute("noticeList", noticeList);
		
		return "main";
	}
	
	// 전당소개 호출
	@GetMapping(value = "/accIntro")
	public String accIntro() {

		return "menu/accIntro";
	}
	
	// 오시는길 호출
	@GetMapping(value = "/wayMap")
	public String wayMap() {

		return "menu/wayMap";
	}
	
	// 이용안내 호출
	@GetMapping(value = "/useinfo")
	public String useinfo() {

		return "menu/useinfo";
	}

	// 권한이 없는 사람이 접근했을경우
	@RequestMapping(value = "/error_user")
	public String error() {
		return "member/error";
	}
	
	// 개인, 법인 선택 페이지
	@RequestMapping(value = "/members/select")
	public String signupSelect() {
		return "member/select";
	}
}