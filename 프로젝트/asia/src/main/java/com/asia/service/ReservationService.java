package com.asia.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asia.dto.ReservationFormDto;
import com.asia.dto.SearchDto;
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

	public void saveReservation(Application application, String name, ReservationFormDto reservationFormDto) throws Exception {

		Member member = memberRepository.findById(name);

		Reservation reservation = new Reservation();

		reservation.setCnt(reservationFormDto.getCnt());
		reservation.setSeat(reservationFormDto.getSeat());
		reservation.setPrice(reservationFormDto.getPrice());
		reservation.setName(reservationFormDto.getName());
		reservation.setUdate(reservationFormDto.getUdate());
		reservation.setPlace(reservationFormDto.getPlace());
		reservation.setRdate(LocalDate.now());
		reservation.setStat("결제대기");
		reservation.setMember(member);
		reservation.setApplication(application);

		reservationRepository.save(reservation);
	}

	@Transactional(readOnly = true)
	public Page<Reservation> getAdminReservationPage(SearchDto searchDto, Pageable pageable) {
		return reservationRepository.getAdminReservationPage(searchDto, pageable);
	}

	public void deleteReservation(Long num) {
		reservationRepository.deleteById(num);
	}

	public Reservation getDtl(Long num) {
		Reservation reservation = reservationRepository.findByNum(num);
		return reservation;
	}

	public void cancelReservation(Long num) {
		String cancel = "취소";
		reservationRepository.updateReservationStat(num, cancel);
	}

	// 변수 이름 수정
	public List<Reservation> getReservationList(Long num) {
		List<Reservation> reservations = reservationRepository.findAllByMemberNumOrderByNumDesc(num);
		return reservations;
	}

	// 환불 추가
	public void refundReservation(Long num) {
		String refund = "환불대기";
		reservationRepository.updateReservationStat(num, refund);
	}

	public void refundComplete(Long num) {
		String complete = "환불완료";
		reservationRepository.updateReservationStat(num, complete);
	}

	public void deleteJoin(Long num) {
		Reservation reservation = reservationRepository.findByApplicationNum(num);
		if (reservation != null) {
			reservation.setApplication(null);
			reservationRepository.save(reservation);
		}
	}
}
