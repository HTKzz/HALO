package com.asia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asia.dto.SearchDto;
import com.asia.entity.Member;

public interface MemberRepositoryCustom {
	
	Page<Member> getMemberMngLists(SearchDto searchDto, Pageable pageable);
	
}
