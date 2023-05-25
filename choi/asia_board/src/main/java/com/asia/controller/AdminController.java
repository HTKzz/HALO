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
import org.springframework.web.bind.annotation.RequestMapping;

import com.asia.entity.Member;
import com.asia.service.AdminMemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class AdminController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	private final AdminMemberService adminMemberService;
	
	@GetMapping(value="/memberMngList")
	public String memberMngList(Model model, @PageableDefault(page = 0, size = 10, sort = "num", direction = Sort.Direction.DESC) Pageable pageable) {
		
		LOGGER.info("어드민 멤버리스트 메서드 호출");
		Page<Member> lists = adminMemberService.memberList(pageable);

		model.addAttribute("memberList", lists);
		
		LOGGER.info("-- 모델 값  : {}", lists.getContent().get(0));
		
		int nowPage = lists.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 9, lists.getTotalPages());
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "admin/member/memberMng";
	}
	
	
	
}
