package com.asia.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchDto {
	
	private String searchBy;   // 검색할때 (상품명 or 아이디) 중에서 어느걸로 검색할지 옵션으로 정한다
	
	private String searchQuery = "";   // 검색어 저장
	
}
