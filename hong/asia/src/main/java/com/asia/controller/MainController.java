package com.asia.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping(value = "/")
	public String main() {
		
		return "main";
	}
}