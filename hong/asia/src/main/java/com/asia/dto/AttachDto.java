package com.asia.dto;

import org.modelmapper.ModelMapper;

import com.asia.entity.Application;
import com.asia.entity.Attach;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AttachDto {

	private Long num;
	private String name;
	private String oriName;
	private String url;
	private String thumb;
	private Application application;

	private static ModelMapper modelMapper = new ModelMapper(); // 멤버 변수로 ModelMapper 객체 추가

	public static AttachDto of(Attach attach) {
		return modelMapper.map(attach, AttachDto.class);
	}

	@QueryProjection
	public AttachDto(Long num, String name, String oriName, String url) {
		this.num = num;
		this.name = name;
		this.oriName = oriName;
		this.url = url;
	}
}
