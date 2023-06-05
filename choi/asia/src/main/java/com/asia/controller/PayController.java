package com.asia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asia.service.PayService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/pays")
@RequiredArgsConstructor
public class PayController {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final PayService payService;
	
	@PostMapping("/new/{num}")
	public String savePay(@PathVariable Long num) {
		
		payService.savePay(num);
		
		return "redirect:/admin/reservationMng";
	}
}
