package com.asia.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.asia.dto.VocFormDto;
import com.asia.entity.Voc;
import com.asia.service.AttachService;
import com.asia.service.VocService;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Controller
@RequiredArgsConstructor
@RequestMapping("/voc")
@ToString
public class VocController {

//	private final Logger LOGGER = LoggerFactory.getLogger(VocController.class);
	private final VocService vocService;
	private final AttachService attachService;

	// 새글
	@GetMapping("/new")
	public String vocForm(Model model) {
		model.addAttribute("vocFormDto", new VocFormDto());

		return "board/voc/vocForm";
	}

	// 새글 등록
	@PostMapping("/new")
	public String newVoc(@Valid VocFormDto vocFormDto, BindingResult bindingResult, Model model,
			@RequestParam("attachFile") List<MultipartFile> attachFileList, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "board/voc/vocForm";
		}

		try {
			String name = principal.getName();
			vocService.saveVoc(vocFormDto, attachFileList, name);

			VocFormDto vocFormDto1 = vocService.getVoc();
			vocService.updateCnt(vocFormDto1.getNum());
			model.addAttribute("voc", vocFormDto1);
			
		} catch (Exception e) {
			model.addAttribute("errorMessage", "등록 중 에러발생");
			return "board/voc/vocForm";
		}
		return "board/voc/vocDetail";
	}

	// 리스트불러오기 페이징넣어서
	@GetMapping("/list")
	public String listVoc(
			@PageableDefault(page = 0, size = 10, sort = "num", direction = Sort.Direction.DESC) Pageable pageable,
			Model model) {

		Page<Voc> list = vocService.getVocLists(pageable);
		model.addAttribute("list", list);

		int nowPage = list.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 4, list.getTotalPages());
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "board/voc/vocList";
	}

	// 상세보기
	@GetMapping("/detail/{num}")
	public String detailVoc(Model model, @PathVariable("num") Long num, Principal principal) {
		VocFormDto vocFormDto = vocService.getvocDtl(num);
		vocService.updateCnt(num);
		model.addAttribute("voc", vocFormDto);

		// 수정 삭제 권한별 비활성화
		String name = principal.getName();
		Voc voc = vocService.findByNum(num);
		model.addAttribute("username", name);
		model.addAttribute("writername", voc.getMember().getId());
		

		return "board/voc/vocDetail";
	}

	// 삭제
	@GetMapping("/delete/{num}")
	public String deleteVoc(@PathVariable Long num) throws Exception {
		attachService.deleteAttach(num);
		vocService.vocDelete(num);
		return "redirect:/voc/list";
	}

	// 수정페이지 진입
	@GetMapping(value = "/update/{num}") // url 경로변수는 {}로 표현
	public String vocDtl(@PathVariable("num") Long num, Model model) {
		try {
			VocFormDto vocFormDto = vocService.getvocDtl(num); // 조회한 데이터를 모델에 담아 뷰로 전달
			model.addAttribute("vocFormDto", vocFormDto);
		} catch (Exception e) { // 엔티티가 존재하지 않을 경우 에러메세지를 담아 등록페이지로 이동
			model.addAttribute("errorMessage", "존재하지 않는 글 입니다.");
			model.addAttribute("vocFormDto", new VocFormDto());
			return "board/voc/vocForm";
		}
		return "board/voc/vocForm";
	}

	// 수정시 작동
	@PostMapping(value = "/update/{num}")
	public String vocUpdate(@Valid VocFormDto vocFormDto, BindingResult bindingResult,
			@RequestParam("attachFile") List<MultipartFile> attachFileList, Model model) {
		if (bindingResult.hasErrors()) {
			return "board/voc/vocForm";
		}
		try {
			vocService.updateVoc(vocFormDto, attachFileList);

		} catch (Exception e) {
			model.addAttribute("errorMessage", "상품 수정 중 에러 발생");
			return "board/voc/vocForm";
		}
		return "redirect:/voc/detail/{num}";
	}

	// 답글
	@GetMapping("/reply/{num}")
	public String replyForm(@PathVariable("num") Long num, Model model) {
		Voc parentVoc = vocService.findByNum(num);

		model.addAttribute("vocFormDto", new VocFormDto());
		model.addAttribute("num", num);
		model.addAttribute("originNo", parentVoc.getOriginNo());

		return "board/voc/vocReply";
	}

	@PostMapping("/reply/new")
	public String newReplyVoc(@Valid VocFormDto vocFormDto, BindingResult bindingResult, Model model,
			@RequestParam("attachFile") List<MultipartFile> attachFileList, @RequestParam("parentNo") Long parentNo,
			@RequestParam("originNo") Long num, Principal principal) {

		try {
			String name = principal.getName();
			
			vocService.saveReplyVoc(vocFormDto, attachFileList, parentNo, name);

		} catch (Exception e) {
			model.addAttribute("errorMessage", "상품 등록 중 에러 발생");
			return "board/voc/vocReply";
		}
		return "redirect:/voc/list";
	}
	
	// 검색 vocController
	@PostMapping("/searchVoc")
	public String searchVoc(@RequestParam("searchOpt") String searchOpt,
			@PageableDefault(page = 0, size = 10, sort = "num", direction = Sort.Direction.DESC) Pageable pageable, Model model, String vocListSearch) {
		
		System.out.println(vocListSearch);
		
		Page<Voc> lists = null;
		if(searchOpt.equals("name")) {
			lists = vocService.vocListSearchByName(vocListSearch, pageable);
		}else if (searchOpt.equals("writer")) {
			lists = vocService.vocListSearchBywriter(vocListSearch, pageable);
		}else {
			return "board/voc/vocList";
		}
		
		model.addAttribute("list", lists);
		
		int nowPage = lists.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 4, lists.getTotalPages());
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "board/voc/vocList";
	}

}
