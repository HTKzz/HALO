package com.asia.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asia.dto.ApplicationDto;
import com.asia.service.ApplicationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final ApplicationService applicationService;
	
	
	
	
	@GetMapping(value = "/")
	public String main(Model model) {
		
		List<ApplicationDto> slideList = applicationService.getSlideList();
		model.addAttribute("slideList", slideList);
		
		return "main";
	}
	
	@GetMapping(value = "/useinfo")
	public String useinfo() {

		return "menu/useinfo";
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