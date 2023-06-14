package com.asia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.asia.dto.CountDto;
import com.asia.dto.SeatDto;
import com.asia.entity.SeatC;

public interface SeatCRepository extends JpaRepository<SeatC, Long> {
	
	@Query("select new com.asia.dto.CountDto(application.num, count(seat)) from SeatC "
			+ "where stat = 'seat' "
			+ "group by application_num")
	List<CountDto> getCount();
	
	@Query("select new com.asia.dto.SeatDto(num, stat, seat) from SeatC where application_num = :num")
	List<SeatDto> getSeat(Long num);

	SeatC findByNum(Long num);
	
}  
