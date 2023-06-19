package com.asia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asia.dto.SearchDto;
import com.asia.entity.Notice;

//사용자정의 인터페이스 작성
public interface NoticeRepositoryCustom {

	public Page<Notice> getNoticeLists(SearchDto searchDto, Pageable pageable);
	
}
