package com.asia.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.asia.entity.BaseEntity;
import com.asia.entity.Board;
import com.asia.entity.Member;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BoardDto extends BaseEntity {
	
	private String name;
	
	private String content;
	
	private Date d_date;
	
	private Integer cnt;
	
	private Long num;
	
	private String createdBy;
	
	private Long originNo;
	
	private Long groupOrd;
	 
    private Long groupLayer;
	
	private Member member;
	
	private List<AttachDto> attachDtoList = new ArrayList<>();
	
	private List<Long> attachNums = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Board createBoard() {
		return modelMapper.map(this, Board.class);
	}
	
	public static BoardDto of(Board board) {
		return modelMapper.map(board, BoardDto.class);
	}
	
	@QueryProjection
	public BoardDto(Long num, Long groupOrd) {
		this.num = num;
		this.groupOrd = groupOrd;
	}
}