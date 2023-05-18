package com.asia.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VocDto {
	
	
	private Long num;
	private String name; // 글제목
	private String content; // 글내용
	private int cnt; // 조회수
	
//	private static ModelMapper modelMapper = new ModelMapper();
//	//modelMappper 이용해서 엔티티 객체와 dto객체 간의 데이터를 복사해 복사한 객체를 반환해주는 메서드
//	
//	public Voc createVoc() {
//		return modelMapper.map(this, Voc.class);
//		//dto -> entity
//	}
//	
//	public static VocDto of(Voc voc) {
//		return modelMapper.map(voc, VocDto.class);
//		//entity -> dto
//	}

//	private LocalDateTime date; // 작성일


}
