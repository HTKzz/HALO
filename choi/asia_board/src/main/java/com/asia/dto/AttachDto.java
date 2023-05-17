package com.asia.dto;

import org.modelmapper.ModelMapper;

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
public class AttachDto{
	
	private Long num;
	
	private String name;
	
	private String oriname;
	
	private String url;
	
	private String thumb;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Attach AttachLists() {
		return modelMapper.map(this, Attach.class);
	}
	
	public static AttachDto of(Attach attach) {
		return modelMapper.map(attach, AttachDto.class);
	}
	
	@QueryProjection
	public AttachDto(Long num, String name) {
		this.num = num;
		this.name = name;
	}
	
}
