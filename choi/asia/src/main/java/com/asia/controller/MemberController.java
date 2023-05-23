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
import com.asia.dto.MemberFormDto;
import com.asia.entity.Member;
import com.asia.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	
	//�엫�쓽濡� 愿�由ъ옄 �깮�꽦
	@PostConstruct
	private void createAdmin() {
		//愿�由ъ옄
		boolean check = memberService.checkIdDuplicate("admin");
		if (check)
			return;
		MemberFormDto memberFormDto = new MemberFormDto();
		memberFormDto.setName("관리자");
		memberFormDto.setId("admin");
		memberFormDto.setPassword("12341234");
		memberFormDto.setEmail("123@naver.com");
		memberFormDto.setTel("0105555555");
		memberFormDto.setBirth("1996-05-23");
		memberFormDto.setAddr("둔산동");
		Member member = Member.createMember(memberFormDto , passwordEncoder);
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		member.setPassword(password);
		member.setRole(Role.ADMIN);
		memberService.saveMember(member);
	}
	
	@GetMapping(value = "/new")
	public String memberForm(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/memberForm";
	}
	
	

//	@PostMapping(value = "/new")
//	public String memberForm(MemberFormDto memberFormDto) {
//		Member member = Member.createMember(memberFormDto, passwordEncoder);
//		memberService.saveMember(member);
//		return "redirect:/";
//	}

	@PostMapping(value = "/new")
	public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
			return "member/memberForm";
		}
		try {
			Member member = Member.createMember(memberFormDto, passwordEncoder);
			memberService.saveMember(member);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/memberForm";
		}
		return "redirect:/";
	}
	
	@GetMapping(value="/identify")
	public String IdentifyUser() {
		return "member/identifyLoginForm";
	}
	
	@GetMapping(value="/login")
	public String loginMember() {
		return "member/memberLoginForm";
	}
	
	@GetMapping(value="/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디와 비밀번호를 확인하세요!");
		return "member/memberLoginForm";
	}
	
	@PostMapping(value="/login")
	public String loginForm(Model model) {
		
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/memberLoginForm";
	}
	
}
