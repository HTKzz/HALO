package com.asia.dto;

import org.modelmapper.ModelMapper;

import com.asia.entity.Attach;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
	
}
