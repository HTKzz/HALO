package com.asia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.asia.dto.CountDto;
import com.asia.dto.SeatDto;
import com.asia.entity.SeatB;

public interface SeatBRepository extends JpaRepository<SeatB, Long> {
	
	@Query("select new com.asia.dto.CountDto(application.num, count(seat)) from SeatB "
			+ "where stat = 'seat' "
			+ "group by application_num")
	List<CountDto> getCount();
	
	@Query("select new com.asia.dto.SeatDto(num, stat, seat) from SeatB where application_num = :num")
	List<SeatDto> getSeat(Long num);

	SeatB findByNum(Long num);
	
}  
