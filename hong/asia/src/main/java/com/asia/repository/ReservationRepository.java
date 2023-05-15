package com.asia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asia.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	

}

