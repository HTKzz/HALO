package com.asia.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asia.dto.ApplicationDto;
import com.asia.dto.CountDto;
import com.asia.service.ApplicationService;
import com.asia.service.SeatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ApplicationService applicationService;
	private final SeatService seatService;
	
	//게시판 페이지 이동
	@GetMapping(value="/application")
	public String application(ApplicationDto applicationDto, Model model, Optional<Integer> page) {
		Pageable pageable = PageRequest.of(page.isPresent()? page.get() : 0 , 10);
		Page<ApplicationDto> list = applicationService.getList1(applicationDto, pageable);
		
        //페이징	        
        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage =  Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage+9, list.getTotalPages());

        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("applicationList", list);
        
        System.out.println(list);
        
        return "application/applicationList";	
	}
	
//	//공지사항 글쓰기 페이지 이동
//	@GetMapping(value="/announcementWrite")
//	public String announcementWrite(@AuthenticationPrincipal User user, Model model) {
//		if(String.valueOf(user.getAuthorities().iterator().next()).equals("ROLE_ADMIN")) {
//			model.addAttribute("freeBoardDto", new FreeBoardDto());
//		} else {
//			model.addAttribute("errorMessage", "관리자만 글을 작성 할수 있습니다.");
//			return "news/announcement";	
//		}
//		return "news/announcementWrite";
//	}
	
//	//공지사항 글 저장
//	@PostMapping(value="/announcementNew")
//	public String announcementNew(@AuthenticationPrincipal User user, @Valid FreeBoardDto freeBoardDto, BindingResult bindingResult, Principal principal, Model model) {
//		if(bindingResult.hasErrors()) {
//			model.addAttribute("errorMessage", "제목과 내용을 입력해주세요.");
//			model.addAttribute("freeBoardDto", freeBoardDto);
//			return "news/announcementWrite";
//		}
//		try {
//			if(String.valueOf(user.getAuthorities().iterator().next()).equals("ROLE_ADMIN")) {
//				Member member = memberService.findByMid(user.getUsername());
//				Announcement announcement = Announcement.createAnnouncement(freeBoardDto);
//				announcementService.announcementSave(announcement, member);
//			} else {
//				model.addAttribute("errorMessage", "관리자만 글을 작성할 수 있습니다.");
//				model.addAttribute("freeBoardDto", freeBoardDto);
//				return "news/announcementWrite";
//			}
//		} catch(Exception e) {
//			e.printStackTrace();
//			model.addAttribute("errorMessage", "글 작성중 에러가 발생했습니다.");
//			model.addAttribute("freeBoardDto", freeBoardDto);
//			return "news/announcementWrite";
//		}
//		return "redirect:/news/announcement";
//	}
	
	//상세 페이지 이동
	@GetMapping(value="/application/{name}")
	public String applicationDetail(Model model, @PathVariable("name") String name) {
		List<ApplicationDto> application = applicationService.getApplicationDtl(name);
		model.addAttribute("application", application.get(0));
		
		List<ApplicationDto> application1 = applicationService.getApplicationSelect(name);
		String seatDetail = application.get(0).getSeatDetail();

		Map<Long, Long> seatMap = new HashMap<>();
		Map<String, String> seatMap1 = new HashMap<>();
	
		if (seatDetail == null) {
			for(ApplicationDto dto : application1) {
				String total = dto.getUdate();
				seatMap1.put(total, Long.toString(dto.getNum()));
			}
			
			Map<String, String> sortedMap = new TreeMap<>(seatMap1);
			model.addAttribute("select", sortedMap);
			
			return "application/applicationDetail1";
		}
		
		if (seatDetail.equals("A")) {
			List<CountDto> seat1 = seatService.getCountA();
			for(CountDto dto : seat1) {
				seatMap.put(dto.getApplication_id(), dto.getCount());
			}
		}
		if (seatDetail.equals("B")) {
			List<CountDto> seat1 = seatService.getCountB();
			for(CountDto dto : seat1) {
				seatMap.put(dto.getApplication_id(), dto.getCount());
			}
		}
		if (seatDetail.equals("C")) {
			List<CountDto> seat1 = seatService.getCountC();
			for(CountDto dto : seat1) {
				seatMap.put(dto.getApplication_id(), dto.getCount());
			}
		}
		
	
		for(ApplicationDto dto : application1) {
			long count = 0;
			if(seatMap.containsKey(dto.getNum())) {
				count = seatMap.get(dto.getNum());
			}
			String total = dto.getUdate() + "일 남은 좌석 : " + Long.toString(count);
			seatMap1.put(total, Long.toString(dto.getNum()));
		}
		
		Map<String, String> sortedMap = new TreeMap<>(seatMap1);
		model.addAttribute("select", sortedMap);
		
		return "application/applicationDetail";
	}
	
//	//공지사항 수정 페이지 이동
//	@GetMapping(value="/announcementModify/{id}")
//	public String announcementModify(@AuthenticationPrincipal User user, @PathVariable("id") Long id, Model model) {
//		Announcement announcement = announcementService.findById(id);
//		if(String.valueOf(user.getAuthorities().iterator().next()).equals("ROLE_ADMIN")) {
//				FreeBoardDto freeBoardDto = FreeBoardDto.of(announcement);
//				model.addAttribute("freeBoardDto", freeBoardDto);
//			} else {
//				model.addAttribute("errorMessage", "관리자가 아니면 수정할 수 없습니다.");
//				FreeBoardDto freeBoardDto = FreeBoardDto.of(announcement);
//				model.addAttribute("freeBoardDto", freeBoardDto);
//				return "news/announcementView";
//			}
//		return "news/announcementUpdate";
//	}
//	
//	//공지사항 수정 등록
//	@PutMapping(value="/announcementUpdate")
//	public String announcementUpdate(@AuthenticationPrincipal User user, Model model,@Valid FreeBoardDto freeBoardDto, BindingResult bindingResult) {
//		if(bindingResult.hasErrors()) {
//			model.addAttribute("errorMessage", "제목과 내용을 입력해주세요.");
//			model.addAttribute("freeBoardDto", freeBoardDto);
//			return "news/announcementUpdate";
//		}
//		try {
//			if(String.valueOf(user.getAuthorities().iterator().next()).equals("ROLE_ADMIN")) {
//				Member member = memberService.findByMid(user.getUsername());
//				Announcement announcementUpdate = Announcement.createAnnouncement(freeBoardDto);
//				announcementService.announcementUpdate(announcementUpdate, member);
//			} else {
//				model.addAttribute("errorMessage", "관리자만 글을 수정할 수 있습니다.");
//				model.addAttribute("freeBoardDto", freeBoardDto);
//				return "news/announcementUpdate";
//			}
//		} catch(Exception e) {
//			e.printStackTrace();
//			model.addAttribute("errorMessage", "글 수정 중 에러가 발생했습니다");
//			model.addAttribute("freeBoardDto", freeBoardDto);
//			return "news/announcementUpdate";
//		}
//		return "redirect:/news/announcement";
//	}
//	
//	//공지사항 삭제하기
//	@DeleteMapping(value="/announcementDelete/{id}")
//	public String announcementDelete(@AuthenticationPrincipal User user, @PathVariable("id") Long id, Model model, Principal principal) {
//		Announcement announcement = announcementService.findById(id);
//		try{
//			if(String.valueOf(user.getAuthorities().iterator().next()).equals("ROLE_ADMIN")) {
//				announcementService.announcementDelete(id);
//				model.addAttribute("succMessage", "글이 삭제 되었습니다.");
//			} else {
//				model.addAttribute("errorMessage", "관리자만 글을 삭제 할 수 있습니다.");
//				FreeBoardDto freeBoardDto = FreeBoardDto.of(announcement);
//				model.addAttribute("freeBoardDto", freeBoardDto);
//				return "news/announcementView";
//			}
//		} catch(Exception e) {
//			model.addAttribute("errorMessage", "글 삭제 중 에러가 발생했습니다.");
//			FreeBoardDto freeBoardDto = FreeBoardDto.of(announcement);
//			model.addAttribute("freeBoardDto", freeBoardDto);
//			return "news/announcementView";
//		}
//		return "redirect:/news/announcement";
//	}
	
}
