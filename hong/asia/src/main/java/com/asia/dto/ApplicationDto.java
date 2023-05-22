package com.asia.dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.asia.entity.Application;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ApplicationDto {
	
	private Long num;
	
	private String name;

	private String sdate;

	private String edate;

	private String udate;

	private String seatDetail;
	
	private int price;
	
	private List<AttachDto> attachDtoList = new ArrayList<>();

	private List<Long> attachIds = new ArrayList<>();

	private static ModelMapper modelMapper = new ModelMapper();

	public Application createApplication() {
		return modelMapper.map(this, Application.class);
	}
	
	public static ApplicationDto of(Application application) {
		return modelMapper.map(application, ApplicationDto.class);
	}
	
	@QueryProjection
	public ApplicationDto(Long num, String name, String sdate, String edate, String udate) {
		this.num = num;
		this.name = name;
		this.sdate = sdate;
		this.edate = edate;
		this.udate = udate;
	}
	
	@QueryProjection
	public ApplicationDto(Long num, String name, String sdate, String edate, String udate, String seatDetail) {
		this.num = num;
		this.name = name;
		this.sdate = sdate;
		this.edate = edate;
		this.udate = udate;
		this.seatDetail = seatDetail;
	}
	
	@QueryProjection
	public ApplicationDto(String name, String sdate, String edate) {
		this.name = name;
		this.sdate = sdate;
		this.edate = edate;
	}
}
