package com.asia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.asia.entity.Board;
import com.asia.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/board")
@Controller
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;
	
	@GetMapping("/boardlist")
	public String articleList(Model model) {   // 데이터를 담아 페이지로 보내기 위해 Model 자료형을 인자로
		model.addAttribute("list", boardService.articleList());
		// boardService에서 생성한 boardlist메소드를 통해 list가 반환되는데 해당 list를 "list"라는 이름으로 넘겨주겠다는 것(html에 나올 수 있게)
		return "board/boardList";
	}
	
	// 게시글 작성 폼 페이지로 이동하기
	@GetMapping("/write")
	public String writearticle() {
		return "board/writeform";
	}
	
	@PostMapping("/add")
	public String addArticle(Board board, Model model, MultipartFile file) throws Exception {
		
		boardService.saveWrite(board, file);
		model.addAttribute("list", boardService.articleList());
		
		return "redirect:/board/boardlist";
	}
	
	// 게시글 상세보기 매핑
	@GetMapping("/detail")
	public String boardDetail(Long num, Model model) {
		model.addAttribute("board", boardService.boardDetail(num));
		return "board/boardDetail";
	}
	
	// 게시글 삭제
	@GetMapping("/delete")
	public String articleDelete(Long num) {
		boardService.articleDelete(num);
		return "redirect:/board/boardlist";   // 삭제 처리 후, boardList로 이동 - redirect해줌
	}
	
	// 게시글 수정 페이지로 가기
	@GetMapping("/modify/{num}")   // 여기에서 num은 path variable의 (주소변수, 경로변수)를 의미
	public String articleModify(@PathVariable("num") Long num, Model model) {
		model.addAttribute("board", boardService.boardDetail(num));
		return "board/modifyform";
	}
	
	@PostMapping("/update/{num}")
	public String articleUpdate(@PathVariable("num") Long num, Board board, MultipartFile file) throws Exception {
		// 기존에 있던글 담아온다
		Board boardTemp = boardService.boardDetail(num);
		
		// 기존에 있던 내용을 새로 수정한 내용으로 덮어쓴다.
		boardTemp.setTitle(board.getTitle());
		boardTemp.setContent(board.getContent());
		
		boardService.saveWrite(boardTemp, file);
		
		return "redirect:/board/boardlist";
	}
		
}

