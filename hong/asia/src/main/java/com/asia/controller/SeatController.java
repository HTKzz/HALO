package com.asia.controller;

import java.text.ParseException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.asia.dto.ApplicationDto;
import com.asia.dto.SeatADto;
import com.asia.entity.SeatA;
import com.asia.service.SeatService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class SeatController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final SeatService seatService;

//	@PostConstruct
//	private void createSeatA() throws ParseException {
//		for (int i = 1; i < 51; i++) {
//			// 임의로 자리생성
//			SeatADto seatADto = new SeatADto();
//
//			seatADto.setStat("use");
//
//			SeatA seatA = SeatA.createSeatA(seatADto);
//			seatService.saveSeatA(seatA);
//		}
//	}
	
	@GetMapping(value="/reservations/seat")
	public String addSeat(Model model, ApplicationDto reservationDto) {
		
		model.addAttribute("seatADto", new SeatADto());
		model.addAttribute("reservationDto", reservationDto);
		return "seat/seatA";
	}
}
