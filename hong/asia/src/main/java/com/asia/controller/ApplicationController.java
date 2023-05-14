package com.asia.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asia.dto.ApplicationDto;
import com.asia.service.ApplicationService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/applications")
@Controller
@RequiredArgsConstructor
public class ApplicationController {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ApplicationService ApplicationService;

//	@PostConstruct
//	private void createApplication() throws ParseException {
//		for (int i = 1; i < 4; i++) {
//			SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
//			String test = "2023-05-03";
//			Date date = sdfYMD.parse(test);
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(date);
//			cal.add(Calendar.DATE, i);
//
//			// 임의로 예약생성
//			ApplicationDto ApplicationDto = new ApplicationDto();
//
//			ApplicationDto.setDate(sdfYMD.format(cal.getTime()));
//			test = sdfYMD.format(cal.getTime());
//
//			ApplicationDto.setSeatDetail("A");
//			for (int i = 1; i < 51; i++) {
//				 
//			}
//
//			Application Application = Application.saveApplication(ApplicationDto);
//			//ApplicationService.saveApplication(ApplicationDto);
//		}
//	}
	
	@PostMapping(value = "/new")
	public String newApplication(@Valid ApplicationDto ApplicationDto, BindingResult bindingResult, Model model,
			Principal principal) {
		
		if (bindingResult.hasErrors()) {
			return "Application/ApplicationForm";
		}
		try {

			String name = principal.getName();
			
			ApplicationService.saveApplication(ApplicationDto, name);
			
		} catch (Exception e) {
			model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
			return "application/applicationForm";
		}
		return "redirect:/";
	}
	
	@GetMapping(value="/add")
	public String addApplication(Model model, Principal principal) {
		model.addAttribute("applicationDto", new ApplicationDto());
		return "application/applicationForm";
	}
	
}
