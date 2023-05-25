package com.asia.controller;

import java.security.Principal;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.asia.dto.BoardDto;
import com.asia.entity.Board;
import com.asia.service.AttachService;
import com.asia.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/boards")
@Controller
@RequiredArgsConstructor
public class BoardController {

	private final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);
	private final BoardService boardService;
	private final AttachService attachService;

	// 게시판 리스트 불러오기
	@GetMapping(value = "/lists")
	public String boardlist(Model model,
			@PageableDefault(page = 0, size = 10, sort = "num", direction = Sort.Direction.DESC) Pageable pageable) {
		LOGGER.info("/boards/lists 메서드 호출");

		Page<Board> lists = boardService.boardList(pageable);
		
		model.addAttribute("boardList", lists);
		

		int nowPage = lists.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 9, lists.getTotalPages());
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "board/boardList";
	}

	// 게시판 글쓰기 폼 불러오기
	@GetMapping(value = "/write")
	public String boardForm(Model model) {
		model.addAttribute("boardDto", new BoardDto());
		LOGGER.info("/boards/write 의 model에 들어온 값 : {}", model);
		return "board/boardForm";
	}

	// 새 글쓰기
	@PostMapping(value = "/submitBoard")
	public String addBoardList(@Valid BoardDto boardDto, Board board, BindingResult bindingResult, Model model,
			Principal principal, @RequestParam("attachFile") List<MultipartFile> attachList) {

		if (bindingResult.hasErrors()) {
			return "board/boardForm";
		}

		if (attachList.get(0).isEmpty() && boardDto.getNum() == null) {
			model.addAttribute("errorMessage", "첫번째 이미지는 필수 입력 값 입니다.");
			return "board/boardForm";
		}

		try {
			String id = principal.getName();

			boardService.writeBoard(boardDto, attachList, id);
			
		} catch (Exception e) {
			model.addAttribute("errorMessage", "등록 중 에러가 발생하였습니다.");
			return "board/boardForm";
		}

		return "redirect:/boards/lists";
	}

	// 글 상세보기
	@GetMapping(value = "/detail/{num}")
	public String boardDetail(@PathVariable("num") Long num, Model model, BoardDto boardDto) {

		LOGGER.info("보드 컨트롤러 디테일 메서드 호출");
		boardDto = boardService.getBoardDetail(num);
		LOGGER.info("boardDto에 들어온 값 {}", boardDto);
		boardService.updateCnt(num);
		model.addAttribute("boardDto", boardDto);
		return "board/boardDetailForm";
	}

	// 글 수정폼 불러오기
	@GetMapping(value = "/modForm/{num}")
	public String modForm(@PathVariable("num") Long num, Model model) {

		LOGGER.info("보드 컨트롤러 모드폼 메서드 호출");
		try {

			BoardDto boardDto = boardService.getBoardDetail(num);
			model.addAttribute("boardDto", boardDto);

		} catch (EntityNotFoundException e) {

			model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
			model.addAttribute("boardDto", new BoardDto());
			return "board/boardList";
		}

		return "board/boardForm";
	}

	// 글 수정하기
	@PostMapping(value = "/modBoard/{num}")
	public String modBoard(@PathVariable("num") Long num, BoardDto boardDto, Model model, BindingResult bindingResult,
			@RequestParam("attachFile") List<MultipartFile> attachList) {

		LOGGER.info("보드 컨트롤러 수정완료 메서드 호출");
		if (bindingResult.hasErrors()) {

			return "board/boardForm";
		}

		if (attachList.get(0).isEmpty() && boardDto.getNum() == null) {
			model.addAttribute("errorMessage", "제목 또는 내용을 입력하세요");

			return "board/boardForm";
		}

		try {
			LOGGER.info("/modBoard/{num} 의 model 값", attachList);
			boardService.updateBoard(boardDto, attachList);

		} catch (Exception e) {
			model.addAttribute("errorMessage", "잘못된 정보 입니다.");

			return "board/boardForm";
		}

		return "redirect:/boards/detail/{num}";
	}

	// 글 삭제하기
	@GetMapping(value = "/deleteBoard/{num}")
	public String deleteBoard(@PathVariable("num") Long num) throws Exception {

		attachService.deleteAttach(num);
		boardService.deleteBoard(num);
		
		return "redirect:/boards/lists";
	}

	// 답글쓰기 폼 풀러오기
	@GetMapping(value = "/replyForm/{num}")
	public String replyForm(@PathVariable("num") Long num, Model model) {
		model.addAttribute("num", num);
		BoardDto boardDto1 = boardService.getBoardDetail(num);
		
		BoardDto boardDto2 = new BoardDto();
		
		boardDto2.setOriginNo(boardDto1.getNum());
		boardDto2.setGroupLayer(boardDto1.getGroupLayer());
		
		model.addAttribute("boardDto", boardDto2);
//		LOGGER.info("답글쓰기 폼 호출 - num : {}", boardDto);
		
		return "board/replyForm";
		}
	
	// 답글 저장
	@PostMapping(value = "/replyBoard")
	public String replyBoard(Principal principal, @RequestParam("attachFile") List<MultipartFile> attachList, BoardDto boardDto, Model model) {
		try {
			String id = principal.getName();
			System.out.println(boardDto);
			boardService.replyBoard(boardDto, attachList, id, model);
			
		}catch(Exception e) {

			return "board/replyForm";
		}
		
		return "redirect:/boards/lists";
	}

}
