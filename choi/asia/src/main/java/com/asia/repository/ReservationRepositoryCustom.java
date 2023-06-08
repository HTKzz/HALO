package com.asia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asia.dto.SearchDto;
import com.asia.entity.Reservation;

public interface ReservationRepositoryCustom  {
	
	Page<Reservation> getAdminReservationPage(SearchDto searchDto, Pageable pageable);
	
}

