package com.asia.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.asia.dto.BoardFormDto;
import com.asia.entity.Board;
import com.asia.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;
	
	// 게시판 글 썼을 때 저장 해줘버리기~
	public String writeBoard(Board board) {
		
		
		boardRepository.save(board);
		return board.getName();
	}
	
	public Long writeBoard(BoardFormDto boardFormDto) {
		
		Board board = boardFormDto.newBoard();
		boardRepository.save(board);
		return board.getNum();
	}
	
	// 게시판 리스트 싹 다 불러오기
	public List<Board> boardLists(){
		
		return boardRepository.findAll();
	}
	
	//게시판 디테일 불러오기
	public BoardFormDto getBoardDetail(Long num) {
		
		Board board = boardRepository.findByNum(num);
		
		BoardFormDto boardFormDto = BoardFormDto.of(board);
		
		
		return boardFormDto;
	}
	
	
}
