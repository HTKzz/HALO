package com.asia.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.asia.dto.AttachDto;
import com.asia.dto.BoardDto;
import com.asia.entity.Attach;
import com.asia.entity.Board;
import com.asia.entity.Member;
import com.asia.repository.AttachRepository;
import com.asia.repository.BoardRepository;
import com.asia.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	private final MemberRepository memberRepository;
	private final AttachRepository attachRepository;
	private final AttachService attachService;
	private final Logger LOGGER = LoggerFactory.getLogger(BoardService.class);

	// 게시판 글 썼을 때 저장 멤버랑 엮기
//	public String writeBoard(BoardDto boardDto, String id) {
//
//		Member member = memberRepository.findById(id);
//		
//		Board board = boardDto.createBoard();
//		
//		board.setMember(member);
//		boardRepository.save(board);
//		return null;
//	}

	// 게시판 글 썻을 때 저장하기.
	public Long writeBoard(@Valid BoardDto boardDto, List<MultipartFile> attachFileList, String id) throws Exception {

		Board board = boardDto.createBoard();
		System.out.println(board);
		
		Member member = memberRepository.findById(id);
		
		board.setMember(member);
		
		boardRepository.save(board);

		for (int i = 0; i < attachFileList.size(); i++) {
			Attach attach = new Attach();
			attach.setBoard(board);
			
			if (i == 0) // 첫 번째 이미지일 경우 대표 상품 이미지 여부 값을 Y로 세팅한다. 나머지 상품 이미지는 N으로 설정한다.
				attach.setThumb("Y");
			else
				attach.setThumb("N");
			attachService.saveAttach(attach, attachFileList.get(i));
		}

		return board.getNum();
	}

	// 게시판 리스트 싹 다 불러오기
	public List<Board> boardList() {

		return boardRepository.findAll();
	}

	// 게시판 디테일 불러오기
	@Transactional
	public BoardDto getBoardDetail(Long num) {

		List<Attach> attachList = attachRepository.findByNumOrderByNumAsc(num);
		List<AttachDto> attachDtoList = new ArrayList<>();

		for (Attach attach : attachList) {

			AttachDto attachDto = AttachDto.of(attach);
			attachDtoList.add(attachDto);
		}

		Board board = boardRepository.findByNum(num);
		BoardDto boardDto = BoardDto.of(board);
		boardDto.setAttachDtoList(attachDtoList);

		LOGGER.info("서비스에서 num에 들어온 값 {}", num);
		return boardDto;
	}

	public Long updateAttach(BoardDto boardDto, List<MultipartFile> attachFileList) throws Exception {

		Board board = boardRepository.findById(boardDto.getNum()).orElseThrow(EntityNotFoundException::new);
		System.out.println(board);
//		board.updateBoard(boardDto);

		List<Long> attachNums = boardDto.getAttachNames();
		for (int i = 0; i < attachFileList.size(); i++) {

			attachService.updateAttach(attachNums.get(i), attachFileList.get(i));
		}

		return board.getNum();
	}

}
