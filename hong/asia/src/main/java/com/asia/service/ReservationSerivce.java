package com.asia.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asia.dto.ReservationFormDto;
import com.asia.entity.Application;
import com.asia.entity.Member;
import com.asia.entity.Reservation;
import com.asia.repository.MemberRepository;
import com.asia.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationSerivce {

	private final MemberRepository memberRepository;
	private final ReservationRepository reservationRepository;
	
	
	public Long saveReservation(ReservationFormDto reservationFormDto, Application application, String name) throws Exception {
		
		Member member = memberRepository.findById(name);
		
		Reservation reservation = reservationFormDto.creatReservation();
		
		reservation.setMember(member);
		reservation.setApplication(application);
		reservation.setStat("결제대기");
		
		reservationRepository.save(reservation);
		
		return null;		
	}
}
