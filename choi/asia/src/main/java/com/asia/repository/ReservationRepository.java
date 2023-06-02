package com.asia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.asia.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, QuerydslPredicateExecutor<Reservation>, ReservationRepositoryCustom {
	
	Reservation findByNum(Long num); 
	
	List<Reservation> findAllByMemberNumOrderByNumDesc(Long num);

	@Modifying
	@Query(value="update Reservation r set r.stat = :statUpdate where r.reservation_num = :num", nativeQuery=true)
	void updateReservationStat(Long num, String statUpdate);
}

