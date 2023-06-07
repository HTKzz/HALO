package com.asia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asia.dto.ApplicationDto;
import com.asia.dto.ApplicationSearchDto;
import com.asia.dto.MainApplicationDto;
import com.asia.entity.Application;

public interface ApplicationRepositoryCustom {
	
	Page<Application> getApplicationList(ApplicationSearchDto applicationSearchDto, Pageable pageable);
	
}
