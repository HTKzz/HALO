package com.asia.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asia.dto.SeatADto;
import com.asia.service.SeatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final SeatService seatService;
	
	//좌석 페이지 이동
	@PostMapping(value="/new")
	public String selectSeat(Model model, @RequestParam("test") int anum, SeatADto seatADto) {
		System.out.println(anum);
		List<SeatADto> seatList = seatService.getSeat(anum);
//		System.out.println(seatList);
		model.addAttribute("seatADto", new SeatADto());
		model.addAttribute("seatList", seatList);
		return "seat/seatA";
	}
}
