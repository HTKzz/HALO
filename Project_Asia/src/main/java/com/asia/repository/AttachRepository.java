package com.asia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.asia.dto.AttachDto;
import com.asia.entity.Attach;

public interface AttachRepository extends JpaRepository<Attach, Long> {
	
	Attach findByNum(Long num);

	List<AttachDto> findByApplicationNum(Long num);

	List<Attach> findByApplicationNumOrderByNumAsc(Long num);

	List<Attach> findByVocNumOrderByNumAsc(Long num);

	List<Attach> findByNoticeNumOrderByNumAsc(Long num);
	
	@Query("select new com.asia.dto.AttachDto(num, name, oriName, url) from Attach where app_num = :num order by att_num asc")
	List<AttachDto> getAppList(Long num);
	
	@Query("select new com.asia.dto.AttachDto(num, name, oriName, url) from Attach where notice_num = :num order by notice_num asc")
	List<AttachDto> getNoticeList(Long num);
	
	@Query("select new com.asia.dto.AttachDto(num, name, oriName, url) from Attach where voc_num = :num order by voc_num asc")
	List<AttachDto> getVocList(Long num);
	
}
