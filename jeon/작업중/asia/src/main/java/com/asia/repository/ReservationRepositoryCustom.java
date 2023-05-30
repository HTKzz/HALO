package com.asia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asia.dto.ReservationSearchDto;
import com.asia.entity.Reservation;

public interface ReservationRepositoryCustom  {
	
	Page<Reservation> getAdminItemPage(ReservationSearchDto reservationSearchDto, Pageable pageable);
	
}

