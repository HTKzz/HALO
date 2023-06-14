package com.asia.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.asia.dto.SearchDto;
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
			model.addAttribute("voc", vocFormDto1);
			
		} catch (Exception e) {
			model.addAttribute("errorMessage", "등록 중 에러발생");
			return "board/voc/vocForm";
		}
		return "board/voc/vocDetail";
	}

	// 리스트불러오기 페이징넣어서
	@GetMapping(value = {"/list", "/list/{page}"})
	public String listVoc(@PathVariable("page") Optional<Integer> page, Model model, SearchDto searchDto) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);

		Page<Voc> list = vocService.getVocLists(searchDto, pageable);
		
		model.addAttribute("maxPage", 10);
		model.addAttribute("list", list);
		
		if (!searchDto.getSearchQuery().matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝|(|)|.|-]*")) {
			searchDto.setSearchQuery("");
		}
		
		model.addAttribute("SearchDto", searchDto);

		return "board/voc/vocList";
	}

	// 상세보기
	@GetMapping("/detail/{num}")
	public String detailVoc(Model model, @PathVariable("num") Long num, Principal principal) {
		
		vocService.updateCnt(num);
		
		VocFormDto vocFormDto = vocService.getvocDtl(num);
		
		model.addAttribute("voc", vocFormDto);
		
		if(principal != null) {
			String name = principal.getName();
			model.addAttribute("username", name);
		}
		
		Voc voc = vocService.findByRealNum(num);
		
		model.addAttribute("writername", voc.getMember().getId());

		return "board/voc/vocDetail";
	}

	// 삭제
	@GetMapping("/delete/{num}")
	public String deleteVoc(@PathVariable Long num, Principal principal, RedirectAttributes re) throws Exception {
		Voc voc = vocService.findByRealNum(num);
		
		if (!(voc.getMember().getId().equals(principal.getName()) || principal.getName().equals("admin"))) {
			re.addFlashAttribute("errorMessage", "작성자 또는 관리자만 삭제가능합니다.");
			return "redirect:/voc/detail/{num}";
		}
		
		attachService.vocDeleteAttach(voc.getNum());
		vocService.vocDelete(num);
		
		return "redirect:/voc/list";
	}

	// 수정페이지 진입
	@GetMapping(value = "/update/{num}") // url 경로변수는 {}로 표현
	public String vocDtl(@PathVariable("num") Long num, Model model, Principal principal, RedirectAttributes re) {
		Voc voc = vocService.findByRealNum(num);
		
		if (!voc.getMember().getId().equals(principal.getName())) {
			re.addFlashAttribute("errorMessage", "작성자만 수정가능합니다.");
			return "redirect:/voc/detail/{num}";
		}
		
		try {
			VocFormDto vocFormDto = vocService.getvocDtl(num); // 조회한 데이터를 모델에 담아 뷰로 전달
			System.out.println(vocFormDto);
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
	public String vocUpdate(@Valid VocFormDto vocFormDto, BindingResult bindingResult, @RequestParam("attachFile") List<MultipartFile> attachFileList, 
			Model model, @PathVariable("num") Long num) {
		
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

	// 답글 페이지 호출
	@GetMapping("/reply/{num}")
	public String replyForm(@PathVariable("num") Long num, Model model) {
		Voc parentVoc = vocService.findByRealNum(num);

		model.addAttribute("vocFormDto", new VocFormDto());
		model.addAttribute("num", num);
		model.addAttribute("originNo", parentVoc.getOriginNo());

		return "board/voc/vocReply";
	}
	
	// 답글
	@PostMapping("/reply/new")
	public String newReplyVoc(@Valid VocFormDto vocFormDto, BindingResult bindingResult, Model model,
			@RequestParam("attachFile") List<MultipartFile> attachFileList, @RequestParam("parentNo") Long parentNo, Principal principal) {

		try {
			String name = principal.getName();
			
			vocService.saveReplyVoc(vocFormDto, attachFileList, parentNo, name);

		} catch (Exception e) {
			model.addAttribute("errorMessage", "상품 등록 중 에러 발생");
			return "board/voc/vocReply";
		}
		return "redirect:/voc/list";
	}
}
