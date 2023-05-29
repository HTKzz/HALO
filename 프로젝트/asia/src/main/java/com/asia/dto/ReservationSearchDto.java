package com.asia.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationSearchDto {
	private String searchDateType;
	private String searchBy;
	private String Stat;
	private String searchQuery = ""; 
}
