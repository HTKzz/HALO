package com.asia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asia.dto.MemberFormDto;
import com.asia.entity.Member;

public interface MemberRepositoryCustom {
	
	Page<Member> getMemberMngLists(MemberFormDto memberFormDto, Pageable pageable);
	
}
