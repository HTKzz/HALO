package com.asia.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asia.dto.ReservationSearchDto;
import com.asia.entity.Application;
import com.asia.entity.Member;
import com.asia.entity.Reservation;
import com.asia.repository.MemberRepository;
import com.asia.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

	private final MemberRepository memberRepository;
	private final ReservationRepository reservationRepository;
	
	public Long saveReservation(Application application, String name, String seat, int cnt, int price) throws Exception {
		
		Member member = memberRepository.findById(name);
		
		Reservation reservation = new Reservation();
		
		reservation.setCnt(cnt);
		reservation.setSeat(seat);
		reservation.setPrice(price);
		reservation.setRdate(LocalDate.now());
		reservation.setStat("결제대기");
		reservation.setMember(member);
		reservation.setApplication(application);
		
		reservationRepository.save(reservation);
		
		return null;		
	}
	
	@Transactional(readOnly=true)
	public Page<Reservation> getAdminReservationPage(ReservationSearchDto reservationSearchDto, Pageable pageable) {
		return reservationRepository.getAdminItemPage(reservationSearchDto, pageable);
	}
	
	
	public void deleteReservation(Long num) {
		reservationRepository.deleteById(num);
    }
	
	public Reservation getDtl(Long num) {
		Reservation reservation = reservationRepository.findByNum(num);
		return reservation;
	}
	
	public void cancleReservation(Long num) {
		String cancle = "취소";
		reservationRepository.updateReservationStat(num, cancle);
	}
	
	//변수 이름 수정
	public List<Reservation> getReservationList(Long num) {
		List<Reservation> reservations = reservationRepository.findAllByMemberNumOrderByNumDesc(num);
		return reservations;
	}

	////환불 추가
	public void refundReservation(Long num) {
		String refund = "환불대기";
		reservationRepository.updateReservationStat(num, refund);
	}
	
	public void refundComplete(Long num) {
		String complete = "환불완료";
		reservationRepository.updateReservationStat(num, complete);
	}
}
