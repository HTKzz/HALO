package com.asia.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MainApplicationDto {
	
	private Long num;
	private String name;
	private String detail;
	private String url;
	private Integer price;
	private String sdate;
	private String edate;
	
	@QueryProjection // 생성자에 @QueryProjection 선언하여 Querydsl로 결과 조회 시 MainItemDto 객체로 바로 받아오도록 활용한다.
	public MainApplicationDto(Long num, String name, String detail, String url, Integer price, String sdate, String edate){
	this.num = num;
	this.name = name;
	this.detail = detail;
	this.url = url;
	this.price = price;
	this.sdate = sdate;
	this.edate = edate;
	}
	
}
