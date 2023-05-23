//package com.asia.dto;
//
//import java.util.Date;
//
//import org.modelmapper.ModelMapper;
//
//import com.asia.entity.Board;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//public class BoardFormDto {
//	
//	private String name;
//	
//	private String content;
//	
//	private Date d_date;
//	
//	private Integer cnt;
//	
//	private String id;
//	
//	private static ModelMapper modelMapper = new ModelMapper();
//	
//	public Board newBoard() {
//		return modelMapper.map(this, Board.class);
//	}
//	
//	public static BoardFormDto of(Board board) {
//		return modelMapper.map(board, BoardFormDto.class);
//	}
//}
