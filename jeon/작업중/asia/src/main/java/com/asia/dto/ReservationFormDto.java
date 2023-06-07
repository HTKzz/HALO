package com.asia.dto;

import java.util.ArrayList;
import java.util.List;

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

	private int cnt;

	private String seat;
	
	private String udate;
	
	private int price;
	
	private String stat;
	
	
}
