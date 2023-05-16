package com.asia.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asia.dto.SeatADto;
import com.asia.dto.SeatBDto;
import com.asia.dto.SeatCDto;
import com.asia.dto.UpdateDto;
import com.asia.entity.Application;
import com.asia.entity.Reservation;
import com.asia.entity.SeatB;
import com.asia.service.ApplicationService;
import com.asia.service.ReservationService;
import com.asia.service.SeatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final SeatService seatService;
	private final ApplicationService applicationService;
	private final ReservationService reservationSerivce;

	// 좌석 페이지 이동
	@PostMapping(value = "/new")
	public String selectSeat(Model model, @RequestParam("test") int anum, @RequestParam("seatDetail") String seat) {

		model.addAttribute("updateDto", new UpdateDto());

		Application application = applicationService.getApplicationDtl1(anum);
		model.addAttribute("name", application.getName());
		model.addAttribute("udate", application.getUdate());
		model.addAttribute("price", application.getPrice());

		model.addAttribute("anum", anum);
		model.addAttribute("seat", seat);

		if (seat.equals("A")) {
			List<SeatADto> seatList = seatService.getSeatA(anum);
			System.out.println(seatList);
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

	//
	@PostMapping(value = "/add")
	public String addreservation(Model model, @RequestParam("anum") int anum, @RequestParam("seat1") String seat1,
			@RequestParam("seat") String seat, @RequestParam("cnt") int cnt, @RequestParam("price") int price,
			Principal principal, UpdateDto updateDto) throws Exception {

		Application application = applicationService.getApplicationDtl1(anum);

		String name = principal.getName();

		seatService.updateSeat(updateDto, anum, seat1);

		reservationSerivce.saveReservation(application, name, seat, cnt, price);

		return "success";
	}

	//
	@PostMapping(value = "/new1")
	public String reservation1(Model model, @RequestParam("test") long anum) {
		Application application = applicationService.getApplicationDtl1(anum);
		model.addAttribute("application", application);
		model.addAttribute("anum", anum);

		return "reservation/reservationForm";
	}

	//
	@PostMapping(value = "/add1")
	public String addreservation1(Model model, @RequestParam("anum") int anum, @RequestParam("cnt") int cnt,
			@RequestParam("price") int price, Principal principal) throws Exception {

		Application application = applicationService.getApplicationDtl1(anum);

		String name = principal.getName();

		String seat = null;

		reservationSerivce.saveReservation(application, name, seat, cnt, price);

		return "success";
	}

	@GetMapping(value = "/delete/{num}")
	public String reservationDelete(@PathVariable Long num) {

		Reservation reservation = reservationSerivce.getDtl(num);
		System.out.println(reservation);

		String seatDetail = reservation.getApplication().getSeatDetail();

		Long deleteSeat = reservation.getApplication().getNum();
		int anum = Long.valueOf(deleteSeat).intValue();

		String selectSeat = reservation.getSeat();
		String[] array = selectSeat.split(",");
		
		seatService.cancelUpdateSeat(seatDetail, anum, array);

		reservationSerivce.deleteReservation(num); 

		return "redirect:/admin/reservationMng";
	}
}
