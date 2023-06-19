package com.asia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.asia.dto.CountDto;
import com.asia.dto.SeatADto;
import com.asia.entity.SeatA;

public interface SeatARepository extends JpaRepository<SeatA, Long> {
	
	@Query("select new com.asia.dto.CountDto(application.num, count(seat)) from SeatA "
			+ "where stat = 'seat' "
			+ "group by application_num")
	List<CountDto> getCount();
	
	@Query("select new com.asia.dto.SeatADto(num, stat, seat) from SeatA where application_num = :num")
	List<SeatADto> getSeat(Long num);

	SeatA findByNum(Long num);
	
}  
