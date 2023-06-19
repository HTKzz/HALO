package com.asia.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReservationFormDto {

	private Long num;
	
	private String name;

	private Long cnt;

	private String seat;
	
	private String udate;
	
	private String place;
	
	private Long price;
	
	private String stat;
}
