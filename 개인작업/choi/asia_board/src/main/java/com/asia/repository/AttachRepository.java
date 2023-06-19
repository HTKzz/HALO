package com.asia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.asia.dto.AttachDto;
import com.asia.entity.Attach;

public interface AttachRepository extends JpaRepository<Attach, Long> {
	
	List<Attach> findByNoticeNumOrderByNumAsc(Long num); 
	
	Attach findByNumAndThumb(Long num, String thumb);
	Attach findByNum(Long num);
	
	@Query("select new com.asia.dto.AttachDto(num, name) from Attach where notice_num = :num")
	List<AttachDto> getAttachLists(Long num);
}
