package com.asia.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.asia.dto.ApplicationDto;
import com.asia.entity.Reservation;
import com.asia.service.ApplicationService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/applications")
@Controller
@RequiredArgsConstructor
public class ApplicationController {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ApplicationService applicationService;
	
	//상품 신청 페이지
	@GetMapping(value = "/new")
	public String addApplication(Model model, Principal principal) {
		
		model.addAttribute("applicationDto", new ApplicationDto());
		
		return "application/applicationForm";
	}
	
	//상품 신청
	@PostMapping(value = "/add")
	public String newApplication(@Valid ApplicationDto applicationDto, BindingResult bindingResult, Model model,
			Principal principal, @RequestParam("attachFile") List<MultipartFile> attachFileList) {
		
		if (bindingResult.hasErrors()) {
			return "application/applicationForm";
		}
		
		if (attachFileList.get(0).isEmpty() && applicationDto.getNum() == null) {
			model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
			return "application/applicationForm";
		}
		
		try {
			
			String name = principal.getName();
			
			applicationService.saveApplication(applicationDto, attachFileList, name);
			
		} catch (Exception e) {
			model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
			return "application/applicationForm";
		}
		
		return "success";
	}
	
}
