package com.asia.dto;

import java.util.Date;

import org.modelmapper.ModelMapper;

import com.asia.entity.BaseEntity;
import com.asia.entity.Board;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BoardDto extends BaseEntity {
	
	private String name;
	
	private String content;
	
	private Date d_date;
	
	private Integer cnt;
	
	private Long num;
	
	private String createdBy;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Board boardLists() {
		return modelMapper.map(this, Board.class);
	}
	
	public static BoardDto of(Board board) {
		return modelMapper.map(board, BoardDto.class);
	}
}