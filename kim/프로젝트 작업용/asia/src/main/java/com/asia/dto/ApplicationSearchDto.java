package com.asia.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApplicationSearchDto {
	
	private String searchDateType;  // 최근으로부터 얼마나 됐는지 기간을 따져 검색
	
	private String searchProgramCategory;   // 프로그램 카테고리에 따른 기준으로 검색
	
	private String searchBy;   // 검색할때 (상품명 or 아이디) 중에서 어느걸로 검색할지 옵션으로 정한다
	
	private String searchQuery = "";   // 검색어 저장
}
