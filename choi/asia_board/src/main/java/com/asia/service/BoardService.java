package com.asia.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.asia.dto.BoardDto;
import com.asia.entity.Board;
import com.asia.entity.Member;
import com.asia.repository.BoardRepository;
import com.asia.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;
	private final MemberRepository memberRepository;
	
	// 게시판 글 썼을 때 저장 해줘버리기~
	public String writeBoard(Board board, String id) {
		
//		Board board =    
		
		Member member = memberRepository.findById(id);
		
		board.setMember(member);
		boardRepository.save(board);
		return null;
	}
	
	public Long writeBoard(BoardDto boardDto) {
		
		Board board = boardDto.boardLists();
		boardRepository.save(board);
		return board.getNum();
	}
	
	// 게시판 리스트 싹 다 불러오기
	public List<Board> boardLists(){
		
		return boardRepository.findAll();
	}
	
	//게시판 디테일 불러오기
	public BoardDto getBoardDetail(Long num) {
		
		Board board = boardRepository.findByNum(num);
		
		BoardDto boardDto = BoardDto.of(board);
		
		
		return boardDto;
	}
	
	
}
