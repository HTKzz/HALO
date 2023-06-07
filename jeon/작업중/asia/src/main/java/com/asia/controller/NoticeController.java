package com.asia.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.asia.dto.NoticeDto;
import com.asia.dto.NoticeSearchDto;
import com.asia.entity.Notice;
import com.asia.service.AttachService;
import com.asia.service.NoticeService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/notices")
@Controller
@RequiredArgsConstructor
public class NoticeController {

	private final NoticeService noticeService;
	private final AttachService attachService;

	
	// 공지 게시판 리스트 불러오기 (페이지, 검색)
	@GetMapping(value = {"/lists", "/lists/{page}"})
	public String noticelist(Model model, @PathVariable("page") Optional<Integer> page, NoticeSearchDto noticeSearchDto) {
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);

		Page<Notice> lists = noticeService.noticeList(noticeSearchDto, pageable);
		
		model.addAttribute("maxPage",10);
		model.addAttribute("noticeList", lists);

		return "board/notice/notice";
	}
	

	// 게시판 글쓰기 폼 불러오기
	@GetMapping(value = "/write")
	public String noticeForm(Model model) {
		
		model.addAttribute("noticeDto", new NoticeDto());
		
		return "board/notice/noticeForm";
	}

	// 새 글쓰기
	@PostMapping(value = "/submitNotice")
	public String addnoticeList(@Valid NoticeDto noticeDto, Notice notice, BindingResult bindingResult, Model model,
			Principal principal, @RequestParam("attachFile") List<MultipartFile> attachList) {

		if (bindingResult.hasErrors()) {
			return "board/notice/noticeForm";
		}

		try {
			String id = principal.getName();

			noticeService.writenotice(noticeDto, attachList, id);
			
		} catch (Exception e) {
			model.addAttribute("errorMessage", "등록 중 에러가 발생하였습니다.");
			return "board/notice/noticeForm";
		}

		return "redirect:/notices/lists";
	}

	// 글 상세보기
	@GetMapping(value = "/detail/{num}")
	public String noticeDetail(@PathVariable("num") Long num, Model model, NoticeDto noticeDto, Principal principal) {
		
		noticeDto = noticeService.getnoticeDetail(num);
		
		noticeService.updateCnt(num);
		model.addAttribute("noticeDto", noticeDto);
		
		String name = principal.getName();
		Notice notice = noticeService.findByNum(num);
		model.addAttribute("username", name);
		model.addAttribute("writername", notice.getMember().getId());
		
		return "board/notice/noticeDetailForm";
	}

	// 글 수정폼 불러오기
	@GetMapping(value = "/modNotice/{num}")
	public String modForm(@PathVariable("num") Long num, Model model) {

		try {

			NoticeDto noticeDto = noticeService.getnoticeDetail(num);
			model.addAttribute("noticeDto", noticeDto);

		} catch (EntityNotFoundException e) {

			model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
			model.addAttribute("noticeDto", new NoticeDto());
			return "board/notice/notice";
		}

		return "board/notice/noticeForm";
	}

	// 글 수정하기
	@PostMapping(value = "/modNotice/{num}")
	public String modnotice(@PathVariable("num") Long num, NoticeDto noticeDto, Model model, BindingResult bindingResult,
			@RequestParam("attachFile") List<MultipartFile> attachList) {
		
		System.out.println(noticeDto);

		if (bindingResult.hasErrors()) {

			return "board/notice/noticeForm";
		}

		try {
			noticeService.updatenotice(noticeDto, attachList);

		} catch (Exception e) {
			model.addAttribute("errorMessage", "잘못된 정보 입니다.");

			return "board/notice/noticeForm";
		}

		return "redirect:/notices/detail/{num}";
	}

	// 글 삭제하기
	@GetMapping(value = "/deleteNotice/{num}")
	public String deletenotice(@PathVariable("num") Long num) throws Exception {

		attachService.deleteAttach(num);
		noticeService.deletenotice(num);
		
		return "redirect:/notices/lists";
	}

	/*
	 * // 답글쓰기 폼 풀러오기
	 * 
	 * @GetMapping(value = "/replyForm/{num}") public String
	 * replyForm(@PathVariable("num") Long num, Model model) {
	 * model.addAttribute("num", num);
	 * 
	 * NoticeDto noticeDto = new NoticeDto();
	 * 
	 * model.addAttribute("noticeDto", noticeDto);
	 * 
	 * return "board/notice/replyForm"; }
	 * 
	 * // 답글 저장
	 * 
	 * @PostMapping(value = "/replyNotice") public String replynotice(Principal
	 * principal, @RequestParam("attachFile") List<MultipartFile> attachList,
	 * NoticeDto noticeDto, Model model) { try { String id = principal.getName();
	 * System.out.println(noticeDto); noticeService.replynotice(noticeDto,
	 * attachList, id, model);
	 * 
	 * }catch(Exception e) {
	 * 
	 * return "board/notice/replyForm"; }
	 * 
	 * return "redirect:/notices/lists"; }
	 */
}
