package com.asia.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asia.dto.NoticeFormDto;
import com.asia.entity.Notice;
import com.asia.service.NoticeService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
	
	private final NoticeService noticeService;
	
	@GetMapping("/new")
	public String noticeForm(Model model) {
		model.addAttribute("noticeFormDto", new NoticeFormDto());
		
		return "notice/noticeForm";
	}
	
	@PostMapping("/new")
	public String newNotice(@Valid NoticeFormDto noticeFormDto, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "notice/noticeForm";
		}
		
		try {
			noticeService.saveNotice(noticeFormDto);
		} catch (Exception e) {
			model.addAttribute("errorMessage","등록 중 에러발생");
			return "notice/noticeForm";
			
		}
		
		
		return "redirect:/";
	}
	

	

}
