package com.asia.dto;

import org.modelmapper.ModelMapper;

import com.asia.entity.Attach;

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
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static AttachDto of(Attach attach) {
		return modelMapper.map(attach, AttachDto.class);
	}
}
