package com.asia.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	private final AdminMemberService adminMemberService;
	
	// 전체회원 리스트 출력
	@GetMapping(value="/memberMngList")
	public String memberMngList(Model model, @PageableDefault(page = 0, size = 10, sort = "num", direction = Sort.Direction.DESC) Pageable pageable,
							 String memberMngSearch) {
		
		Page<Member> lists = adminMemberService.memberList(pageable);
		
		model.addAttribute("memberMngList", lists);
		
		LOGGER.info("-- 리스트 값  : {}", lists.getContent().get(0));
		
		int nowPage = lists.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 9, lists.getTotalPages());
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "admin/member/memberMng";
	}
	
	@PostMapping(value="/searchMember")
	public String searchMember(@RequestParam("searchOption") String searchOption, @PageableDefault(page = 0, size = 10, sort = "num", direction = Sort.Direction.DESC) Pageable pageable,
			String memberMngSearch, Model model) {
		
		System.out.println(searchOption);
		
		Page<Member> lists = null;
		if(memberMngSearch.equals("name")) {
			lists = adminMemberService.searchMemberByName(memberMngSearch, pageable);
		
		}else if(searchOption == "tel") {
			lists = adminMemberService.searchMemberByTel(memberMngSearch, pageable);
		
		}else if(searchOption == "email") {
			lists = adminMemberService.searchMemberByEmail(memberMngSearch, pageable);
		
		}else if(searchOption == "birth") {
			lists = adminMemberService.searchMemberByBirth(memberMngSearch, pageable);
		
		}else if(searchOption == "regTime") {
			lists = adminMemberService.searchMemberByRegTime(memberMngSearch, pageable);
		
		}else if(searchOption == "stat") {
			lists = adminMemberService.searchMemberByStat(memberMngSearch, pageable);
		
		}else if(searchOption == "role") {
			lists = adminMemberService.searchMemberByRole(memberMngSearch, pageable);
		
		}else {
			return "admin/member/memberMng";
		}
		
		System.out.println();
		
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
