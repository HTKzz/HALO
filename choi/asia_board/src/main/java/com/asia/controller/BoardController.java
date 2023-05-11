package com.asia.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asia.dto.BoardFormDto;
import com.asia.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/boards")
@Controller
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	
	// 게시판 리스트 불러와보리기~
	@GetMapping(value="/lists")
	public String boardlist(Model model){
		
		model.addAttribute("boardList", boardService.boardLists());
		return "board/boardList";
	}
	
	//게시판 글쓰기 폼 불러와보리기~
	@GetMapping(value="/write")
	public String boardForm(Model model){
		model.addAttribute("BoardFormDto", new BoardFormDto());
		return "board/boardForm";
	}
	
	//게시판 글 다써서 서브밋 해보리기~
	@PostMapping(value="/submitBoard")
	public String addBoardList(@Valid BoardFormDto boardFormDto) {
		
		
		return "/board";
	}
	
}

