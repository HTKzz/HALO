package com.asia.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SeatCDto {
	
	private Long num;

	private String stat;
	
	private String seat;
	
	@QueryProjection
	public SeatCDto(Long num, String seat) {
		this.num = num;
		this.seat = seat;
	}
	
	@QueryProjection
	public SeatCDto(Long num, String stat, String seat) {
		this.num = num;
		this.stat = stat;
		this.seat = seat;
	}
	
	
}
