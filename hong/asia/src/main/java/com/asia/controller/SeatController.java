package com.asia.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asia.dto.SeatADto;
import com.asia.dto.UpdateDto;
import com.asia.entity.SeatA;
import com.asia.service.SeatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SeatController {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

}
