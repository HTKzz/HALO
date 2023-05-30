package com.asia.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.asia.dto.ApplicationDto;
import com.asia.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long>, QuerydslPredicateExecutor<Application>, ApplicationRepositoryCustom {
	
	List<Application> findByNameOrDetail(String name, String detail);
	List<Application> findByPriceLessThan(Integer price);
	List<Application> findByPriceLessThanOrderByPriceDesc(Integer price);
	List<Application> findByrat(String rat);
	List<Application> findByRunLessThan(Integer run);
	List<Application> findByRunLessThanOrderByRunDesc(Integer run);
	void deleteByNum(Long num);
	Page<Application> findAll(Pageable pageable);
	Page<Application> findByNameContaining(String searchKeyword, Pageable pageable);
	List<Application> findByProgramCategory(String programCategory);
	
	@Query("select distinct new com.asia.dto.ApplicationDto(name, sdate, edate) from Application where program_Category = :programCategory")
	List<ApplicationDto> getList1(String programCategory);
	
	List<Application> findByName(String name);
	
	@Query("select distinct new com.asia.dto.ApplicationDto(num, name, sdate, edate, udate) from Application "
			+ "where name = :name")
	List<ApplicationDto> getList2(String name);
	
	Application findByNum(long anum);
}
