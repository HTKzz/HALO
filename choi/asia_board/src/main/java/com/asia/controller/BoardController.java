package com.asia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asia.dto.BoardDto;
import com.asia.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/boards")
@Controller
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	private final BoardDto boardDto;
	
}
