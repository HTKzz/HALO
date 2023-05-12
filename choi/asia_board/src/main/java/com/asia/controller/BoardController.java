package com.asia.controller;

import java.util.List;

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

import com.asia.dto.BoardDto;
import com.asia.dto.BoardFormDto;
import com.asia.entity.Board;
import com.asia.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/boards")
@Controller
@RequiredArgsConstructor
public class BoardController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);
	private final BoardService boardService;
	
	
	// 게시판 리스트 불러와보리기~
	@GetMapping(value="/lists")
	public String boardlist(Model model){
		LOGGER.info("/boards/lists 메서드 호출");
		
		model.addAttribute("boardList", boardService.boardLists());
		LOGGER.info("model에 들어온 값 : {}", model);
		return "board/boardList";
	}
	
	//게시판 글쓰기 폼 불러와보리기~
	@GetMapping(value="/write")
	public String boardForm(Model model){
		model.addAttribute("BoardDto", new BoardDto());
		model.addAttribute("BoardFormDto", new BoardFormDto());
		LOGGER.info("model에 들어온 값 : {}", model);
		return "board/boardForm";
	}
	
	//게시판 글 다써서 서브밋 후 리스트 불러오기
	@PostMapping(value="/submitBoard")
	public String addBoardList(@Valid BoardFormDto boardFormDto, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "board/boardForm";
		}
		try {
			boardService.writeBoard(boardFormDto);
		
		}catch(Exception e) {
			return "board/boardForm";
		}
		
		model.addAttribute(boardFormDto);
		LOGGER.info("model에 들어온 값 : {}", model);
		return "redirect:/boards/lists";
	}
	
	// 리스트 불러오기
	@GetMapping(value="/board")
	public String boardLists(BoardFormDto boardFormDto, Model model){
		
		List<Board> List = boardService.boardLists();
		model.addAttribute("BoardFormDto", List);
		return "board/boardList";
	}
	
	@GetMapping(value="/detail/{num}")
	public String boardDetail(@PathVariable("num")Long num, Model model) {
		
		BoardFormDto boardFormDto = boardService.getBoardDetail(num);
		model.addAttribute("detail", boardFormDto);
		LOGGER.info("/board/detail/{num} 의 값 {} :", num);
		return "board/boardDetail";
	}
	
}

