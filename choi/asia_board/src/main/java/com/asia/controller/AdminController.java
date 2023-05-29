package com.asia.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asia.entity.Member;
import com.asia.service.AdminMemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class AdminController {
	
	private final AdminMemberService adminMemberService;
	
	// 전체회원 리스트 출력
	@GetMapping(value="/memberMngList")
	public String memberMngList(Model model, @PageableDefault(page = 0, size = 10, sort = "num", direction = Sort.Direction.DESC) Pageable pageable,
							 String memberMngSearch) {
		
		Page<Member> lists = adminMemberService.memberList(pageable);
		
		model.addAttribute("memberMngList", lists);
		
		int nowPage = lists.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 9, lists.getTotalPages());
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "admin/member/memberMng";
	}
	
	// 회원관리 -> 검색
	@PostMapping(value="/searchMember")
	public String searchMember(@RequestParam("searchOption") String searchOption, @PageableDefault(page = 0, size = 10, sort = "num", direction = Sort.Direction.DESC) Pageable pageable,
			String memberMngSearch, Model model) {
		
//		System.out.println(searchOption);
		
		Page<Member> lists = null;
		if(searchOption.equals("name")) {
			lists = adminMemberService.searchMemberByName(memberMngSearch, pageable);
		
		}else if(searchOption.equals("tel")) {
			lists = adminMemberService.searchMemberByTel(memberMngSearch, pageable);
		
		}else if(searchOption.equals("email")) {
			lists = adminMemberService.searchMemberByEmail(memberMngSearch, pageable);
		
		}else if(searchOption.equals("birth")) {
			lists = adminMemberService.searchMemberByBirth(memberMngSearch, pageable);
		
		}else if(searchOption.equals("join")) {
			lists = adminMemberService.searchMemberByJoin(memberMngSearch, pageable);
			
//		}else if(searchOption.equals("stat")) {
//			lists = adminMemberService.searchMemberByStat(memberMngSearch, pageable);
//			
//		}else if(searchOption.equals("role")) {
//			System.out.println("들어옴");
//			lists = adminMemberService.searchMemberByRole(memberMngSearch, pageable);
			
		}else {
			return "admin/member/memberMng";
		}
		
		model.addAttribute("memberMngList", lists);
		
		int nowPage = lists.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 9, lists.getTotalPages());
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		return "admin/member/memberMng";
	}
	
}
