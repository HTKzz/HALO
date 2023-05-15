package com.asia.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.modelmapper.ModelMapper;

import com.asia.entity.Application;
import com.asia.entity.Member;
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

	private String startdate;

	private String enddate;

	private String udate;

	private String seatDetail;
	
	private int price;
	
	private Long member;

	private static ModelMapper modelMapper = new ModelMapper();

	public Application createApplication() {
		return modelMapper.map(this, Application.class);
	}
	
	public static ApplicationDto of(Application application) {
		return modelMapper.map(application, ApplicationDto.class);
	}
	
	@QueryProjection
	public ApplicationDto(Long num, String name, String startdate, String enddate, String udate) {
		this.num = num;
		this.name = name;
		this.startdate = startdate;
		this.enddate = enddate;
		this.udate = udate;
	}
	
	@QueryProjection
	public ApplicationDto(Long num, String name, String startdate, String enddate, String udate, String seatDetail) {
		this.num = num;
		this.name = name;
		this.startdate = startdate;
		this.enddate = enddate;
		this.udate = udate;
		this.seatDetail = seatDetail;
	}
	
	@QueryProjection
	public ApplicationDto(String name, String startdate, String enddate) {
		this.name = name;
		this.startdate = startdate;
		this.enddate = enddate;
	}
}
