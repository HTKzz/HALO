package com.asia.dto;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.asia.entity.Notice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeFormDto {
	
	@NotBlank(message = "글제목은 필수 입력 값입니다.")
	private String name; // 글제목

	private String content; // 글내용
	
	private static ModelMapper modelMapper = new ModelMapper();

	public Notice createNotice() {
		return modelMapper.map(this, Notice.class);
		
	}

	
//	private LocalDateTime date; // 작성일
//	private String cnt; // 조회수

}
