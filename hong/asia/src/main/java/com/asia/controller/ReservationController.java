package com.asia.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asia.dto.ReservationFormDto;
import com.asia.dto.SeatADto;
import com.asia.dto.SeatBDto;
import com.asia.dto.SeatCDto;
import com.asia.dto.UpdateDto;
import com.asia.entity.Application;
import com.asia.service.ApplicationService;
import com.asia.service.ReservationSerivce;
import com.asia.service.SeatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final SeatService seatService;
	private final ApplicationService applicationService;
	private final ReservationSerivce reservationSerivce;

	// 좌석 페이지 이동
	@PostMapping(value = "/new")
	public String selectSeat(Model model, @RequestParam("test") int anum, @RequestParam("seatDetail") String seat) {
		
		Application application = applicationService.getApplicationDtl1(anum);
		model.addAttribute("application", application);
		
		model.addAttribute("updateDto", new UpdateDto());
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("application", application);
		map.put("updateDto", new UpdateDto());
		
		model.addAttribute("map", map);
		
		model.addAttribute("anum", anum);
		model.addAttribute("seat", seat);

		if (seat.equals("A")) {
			List<SeatADto> seatList = seatService.getSeatA(anum);
			model.addAttribute("seatList", seatList);

			return "seat/seatA";
		}

		if (seat.equals("B")) {
			List<SeatBDto> seatList = seatService.getSeatB(anum);
			model.addAttribute("seatList", seatList);

			return "seat/seatB";
		}

		if (seat.equals("C")) {
			List<SeatCDto> seatList = seatService.getSeatC(anum);
			model.addAttribute("seatList", seatList);

			return "seat/seatC";
		}
		return null;
	}

	// 좌석 페이지 이동
	@PostMapping(value = "/new1")
	public String reservation1(Model model, @RequestParam("test") long anum) {
		System.out.println(anum);
		Application application = applicationService.getApplicationDtl1(anum);
		model.addAttribute("application", application);
		model.addAttribute("anum", anum);
		
		return "reservation/reservationForm";
	}
	
	@PostMapping(value = "/add")
	public String addreservation(@Valid ReservationFormDto reservationFormDto, Model model, @RequestParam("anum") int anum,
			Principal principal, BindingResult bindingResult, @RequestParam("seat") String seat, @Valid UpdateDto updateDto) throws Exception {
		
		Application application = applicationService.getApplicationDtl1(anum);
		
		String name = principal.getName();
		
		System.out.println(updateDto);
		
		seatService.updateSeat(updateDto, anum, seat);
		
		reservationSerivce.saveReservation(reservationFormDto, application, name);
		
		return "seat/success";
	}
}
