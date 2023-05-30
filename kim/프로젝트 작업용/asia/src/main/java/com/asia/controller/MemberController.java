package com.asia.controller;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asia.constant.Role;
import com.asia.dto.CompanyFormDto;
import com.asia.dto.MemberFormDto;
import com.asia.entity.Member;
import com.asia.service.MailService;
import com.asia.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final MemberService memberService;
	private final MailService mailService;
	private final PasswordEncoder passwordEncoder;
	
	//임의로 관리자 생성
	@PostConstruct
	private void createAdmin() {
		//관리자
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
		memberFormDto.setAddr("관저동");
		Member member = Member.createMember(memberFormDto , passwordEncoder);
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		member.setPassword(password);
		member.setRole(Role.ADMIN);
		memberService.saveMember(member);
	}
	
	//일반 회원가입 페이지 불러오기
	@GetMapping(value = "/member/new")
	public String memberForm(Model model) {
		MemberFormDto memberFormDto = new MemberFormDto();
		memberFormDto.setAgree("Y");
		model.addAttribute("memberFormDto", memberFormDto);
		return "member/memberForm";
	}
	
	//일반 회원가입 페이지 불러오기
		@GetMapping(value = "/company/new")
		public String companyForm(Model model) {
			CompanyFormDto companyFormDto = new CompanyFormDto();
			companyFormDto.setAgree("Y");
			model.addAttribute("companyFormDto", companyFormDto);
			return "member/companyForm";
		}

	//회원가입
	@PostMapping(value = "/memberadd")
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
	
	@PostMapping(value = "/companyadd")
	public String newCompany(@Valid CompanyFormDto companyFormDto, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "member/memberForm";
		}
		try {
				Member member = Member.createCompany(companyFormDto, passwordEncoder);
				memberService.saveMember(member);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/memberForm";
		}
		return "redirect:/";
	}
	
	//로그인
	@GetMapping(value="/login")
	public String loginMember() {
		return "/member/memberLoginForm";
	}
	
	//로그인 에러
	@GetMapping(value="/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
		return "/member/memberLoginForm";
	}
	
	@GetMapping(value="/idpw")
	public String findIdPw() {
		return "/member/findIdPw";
	}
	
	@PostMapping(value="/findid")
	@ResponseBody
	public HashMap<String, Object> findId(@RequestParam("name") String name, @RequestParam("email") String email) throws MessagingException {
		Member member = memberService.findByNameAndEmail(name, email);
		HashMap<String, Object> map = new HashMap<>();
		map.put("result", mailService.sendFindIdMail(email, member));
		return map;
	}
	
	@PostMapping(value="/findpw")
	@ResponseBody
	public String findPw(String id, String email) throws MessagingException, InterruptedException, ExecutionException {
		Member member = memberService.findByIdAndEmail(id, email);
		String password = mailService.sendFindPwMail(email, member).get();
		String pw = passwordEncoder.encode(password);
		member.setPassword(pw);
		memberService.updateMember(member);
		return "success";
	}
}
