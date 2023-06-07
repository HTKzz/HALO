package com.asia.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VocSearchDto {
	private String searchBy;
	private String searchQuery = ""; 
}
