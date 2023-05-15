package com.asia.controller;

import java.security.Principal;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.asia.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/boards")
@Controller
@RequiredArgsConstructor
public class BoardController {

	private final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);
	private final BoardService boardService;
//	private final AttachService attachService;

	// 게시판 리스트 불러오기
	@GetMapping(value = "/lists")
	public String boardlist(Model model) {
		LOGGER.info("/boards/lists 메서드 호출");

		model.addAttribute("boardList", boardService.boardList());
		LOGGER.info("model에 들어온 값 : {}", model);
		return "board/boardList";
	}

	// 게시판 글쓰기 폼 불러오기
	@GetMapping(value = "/write")
	public String boardForm(Model model) {
		model.addAttribute("boardDto", new BoardDto());
		LOGGER.info("/boards/write 의 model에 들어온 값 : {}", model);
		return "board/boardForm";
	}

	// 게시판 글 다써서 서브밋 후 리스트 불러오기
	@PostMapping(value = "/submitBoard")
	public String addBoardList(@Valid BoardDto boardDto, Board board, BindingResult bindingResult, Model model,
			Principal principal, @RequestParam("attachFile") List<MultipartFile> attachFileList) {

		if (bindingResult.hasErrors()) {
			return "board/boardForm";
		}

		if (attachFileList.get(0).isEmpty() && boardDto.getNum() == null) {
			model.addAttribute("errorMessage", "첫번째 이미지는 필수 입력 값 입니다.");
			return "item/itemForm";
		}

		try {
			String id = principal.getName();
			System.out.println(id);

			boardService.writeBoard(boardDto, attachFileList, id);
//			attachService.saveAttach(Attach.addBoard(), boardDto.getNum());

		} catch (Exception e) {
			model.addAttribute("errorMessage", "등록 중 에러가 발생하였습니다.");
			return "board/boardForm";
		}

		return "redirect:/boards/lists";
	}

	// 리스트 불러오기
	@GetMapping(value = "/board")
	public String boardList(BoardDto boardDto, Model model) {

		List<Board> List = boardService.boardList();
		model.addAttribute("boardDto", List);
		return "board/boardList";
	}

	// 글 상세보기
	@GetMapping(value = "/detail/{num}")
	public String boardDetail(@PathVariable("num") Long num, Model model, BoardDto boardDto) {
		System.out.println(num);

		boardDto = boardService.getBoardDetail(num);
		LOGGER.info("디테일 boardDto {}", boardDto);
		model.addAttribute("mem_num");
		model.addAttribute("boardDto", boardDto);
//		model.addAttribute("detail", boardFormDto);
		LOGGER.info("/board/detail/{num} 의 값 {} :", num);
		LOGGER.info("/board/detail/{num} 의 model값 {} :", model);
		return "board/boardDetailForm";
	}

	// 글 수정폼 불러오기
	@GetMapping(value = "/modForm/{num}")
	public String modForm(@PathVariable("num") Long num, Model model) {
		
		LOGGER.info("/boards/modForm/ 의 num값 {} :", num);
		
		try {
			
			BoardDto boardDto = boardService.getBoardDetail(num);
			model.addAttribute("mem_num");
			model.addAttribute("boardDto", boardDto);
		
		}catch(EntityNotFoundException e) {
			
			model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
			model.addAttribute("boardDto", new BoardDto());
			return "board/boardList";
		}

		LOGGER.info("/boards/modForm/ 의 model값 {} :", model);

		return "board/modForm";
	}

	// 글 수정하기
	@PostMapping(value = "/modBoard/{num}")
	public String modBoard(@PathVariable("num") Long num, BoardDto boardDto, Model model, BindingResult bindingResult,
			@RequestParam("attachFile") List<MultipartFile> attachFileList) {
//		
		if (bindingResult.hasErrors()) {
			return "board/modForm";
		}

		if (attachFileList.get(0).isEmpty() && boardDto.getNum() == null) {
			model.addAttribute("errorMessage", "제목 또는 내용을 입력하세요");
			return "board/modForm";
		}

		try {
//			boardDto = boardService.getBoardDetail(num);
			boardService.updateAttach(boardDto, attachFileList);

//			Board board = new Board();
//			board.setNum(boardDto.getNum());
//			board.setName(boardDto.getName());
//			board.setContent(boardDto.getContent());
//			
//			model.addAttribute("board", board);

		} catch (Exception e) {

			e.printStackTrace();
			model.addAttribute("errorMessage", "잘못된 정보 일겁니다.");
			return "board/modForm";
		}

		return "redirect:/boards/lists";
	}

}
