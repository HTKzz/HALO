package com.asia.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
	
	private Long originNo;
	
	@NotBlank(message = "※ 프로그램명은 필수 입력 값입니다.")
	private String name;

	@NotNull(message = "※ 프로그램 가격은 필수 입력 값입니다.")
	private Integer price;
	
	@NotBlank(message = "※ 관람등급은 필수 입력 값입니다.")
	private String rat;
	
	@NotNull(message = "※ 관람시간은 필수 입력 값입니다.")
	private Integer run;

	@NotBlank(message = "※ 프로그램 기간은 필수 입력 값입니다.")
	private String sdate;
	
	@NotBlank(message = "※ 프로그램 기간은 필수 입력 값입니다.")
	private String edate;
	
	private String udate;
	
//	@NotBlank(message = "※ 좌석은 필수 선택 값입니다.")
	private String seatDetail;
	
	@NotBlank(message = "※ 상세정보는 필수 입력 값입니다.")
	private String detail;
	
	private String programCategory;
	
	private String place;
	
	private String url;
	
	private String approvalStatus;
	
	private List<AttachDto> attachDtoList = new ArrayList<>();   // 프로그램 저장 후 수정할때 첨부파일 정보를 저장하는 리스트
	
	private List<Long> attachIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Application createApplication() {
		return modelMapper.map(this, Application.class);
	}   // dto -> entity
	
	public static ApplicationDto of(Application application) {
		return modelMapper.map(application, ApplicationDto.class);
		// entity -> dto
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
	
	@QueryProjection
	public ApplicationDto(String name, String sdate, String edate, String url) {
		this.name = name;
		this.sdate = sdate;
		this.edate = edate;
		this.url = url;
	}
}
