package com.asia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asia.entity.Voc;

//사용자정의 인터페이스 작성
public interface VocRepositoryCustom {

	Page<Voc> getVocLists(Pageable pageable);


	
	
	
	
}
