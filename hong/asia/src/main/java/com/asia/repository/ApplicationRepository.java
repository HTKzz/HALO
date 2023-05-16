package com.asia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.asia.dto.ApplicationDto;
import com.asia.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
	
	@Query("select distinct new com.asia.dto.ApplicationDto(name, startdate, enddate) from Application")
	List<ApplicationDto> getList1();
	
	@Query("select distinct new com.asia.dto.ApplicationDto(num, name, startdate, enddate, udate) from Application "
			+ "where name = :name")
	List<ApplicationDto> getList2(String name);
	
	@Query("select distinct new com.asia.dto.ApplicationDto(num, name, startdate, enddate, udate, seatDetail) from Application "
			+ "where name = :name")
	List<ApplicationDto> getApplication(String name);
	
	Application findByNum(long anum);
	
}

