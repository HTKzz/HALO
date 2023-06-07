package com.asia.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoticeSearchDto {
	private String searchBy;
	private String searchQuery = "";
}
