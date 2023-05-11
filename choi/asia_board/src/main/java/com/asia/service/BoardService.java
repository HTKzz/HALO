package com.asia.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.asia.entity.Board;
import com.asia.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;
	
	// 게시판 글 썼을 때 저장 해줘버리기~
	public void writeBoard(Board board) {
		
		boardRepository.save(board);
	}
	
	// 게시판 리스트 싹 다 불러오기
	public List<Board> boardLists(){
		
		return boardRepository.findAll();
	}
	
	
}
