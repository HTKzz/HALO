package com.asia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.asia.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, QuerydslPredicateExecutor<Reservation>, ReservationRepositoryCustom {
	
	Reservation findByNum(Long num); 
	
}

