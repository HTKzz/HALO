package com.asia.controller;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asia.constant.Role;
import com.asia.dto.CompanyFormDto;
import com.asia.entity.Company;
import com.asia.service.CompanyService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/companys")
@Controller
@RequiredArgsConstructor
public class CompanyController {
	
	private final CompanyService companyService;
	private final PasswordEncoder passwordEncoder;
	
	@PostConstruct
	private void createCompany() {
		boolean check = companyService.checkIdDuplicate("company");
		
		if (check)
			return;
		CompanyFormDto companyFormDto = new CompanyFormDto();
		companyFormDto.setName("법인가입테스트");
		companyFormDto.setId("company");
		companyFormDto.setPassword("12341234");
		Company company = Company.createCompany(companyFormDto , passwordEncoder);
		String password = passwordEncoder.encode(companyFormDto.getPassword());
		company.setPassword(password);
		company.setRole(Role.COMPANY);
		companyService.saveCompany(company);
	}
	
	@PostMapping(value="/new")
	public String newCompany(@Valid CompanyFormDto companyFormDto, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "redirect:/";
		}
		try {
			Company company = Company.createCompany(companyFormDto, passwordEncoder);
			companyService.saveCompany(company);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/memberForm";
		}
		return "redirect:/";
	}
	
	@PostMapping(value="/login")
	public String loginCompany2(Model model) {
		
		model.addAttribute("companyFormDto", new CompanyFormDto());
		System.out.println(model);
		return "member/companyLoginForm";
	}
	
	@GetMapping(value="/new")
	public String CompanyForm(Model model) {
		model.addAttribute("companyFormDto", new CompanyFormDto());
		return "member/companyForm";
	}
	
	@GetMapping(value="/login")
	public String loginCompany() {
		return "member/companyLoginForm";
	}
	
	@GetMapping(value="/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디와 비밀번호를 확인하세요!");
		return "member/companyLoginForm";
	}
	
	
	
}
