package com.asia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.asia.entity.Reservation;

public interface ReservationRepository
		extends JpaRepository<Reservation, Long>, QuerydslPredicateExecutor<Reservation>, ReservationRepositoryCustom {

	Reservation findByNum(Long num);
	
	Reservation findByApplicationNum(Long num);

	// 변수 이름 수정(조회조건)
	List<Reservation> findAllByMemberNumOrderByNumDesc(Long num);

	// 취소완료, 환불대기, 환불완료 쿼리변경
	@Modifying
	@Query(value = "update Reservation r set r.stat = :statUpdate where r.reservation_num = :num", nativeQuery = true)
	void updateReservationStat(Long num, String statUpdate);

}
