package com.asia.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BoardDto {
	
	private String name;
	
	private String content;
	
	private Date d_date;
	
	private Integer cnt;
	
}