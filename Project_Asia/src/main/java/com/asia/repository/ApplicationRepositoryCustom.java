package com.asia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asia.dto.SearchDto;
import com.asia.entity.Application;

public interface ApplicationRepositoryCustom {
	
	Page<Application> getApplicationList(SearchDto searchDto, Pageable pageable);
	
	Page<Application> getMyApplicationList(SearchDto searchDto, Pageable pageable, String id);
	
}
