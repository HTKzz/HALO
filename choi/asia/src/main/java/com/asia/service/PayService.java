package com.asia.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asia.entity.Pay;
import com.asia.entity.Reservation;
import com.asia.repository.PayRepository;
import com.asia.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PayService {

//	private final Logger LOGGER = LoggerFactory.getLogger(PayService.class);
	
	private final PayRepository payRepository;
	private final ReservationRepository reservationRepository;
	
	public void savePay(Long num) {
		
		Reservation reservation = reservationRepository.findByNum(num);
		
		reservation.setStat("결제완료");
		
		Pay pay = Pay.createPay();
		pay.setReservation(reservation);
		payRepository.save(pay);
		
	}
	
}
