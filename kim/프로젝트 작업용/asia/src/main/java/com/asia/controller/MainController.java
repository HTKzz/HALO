package com.asia.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping(value = "/")
	public String main() {

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