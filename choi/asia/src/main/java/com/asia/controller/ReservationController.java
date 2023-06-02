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
import com.asia.entity.Member;
import com.asia.entity.Reservation;
import com.asia.service.ApplicationService;
import com.asia.service.MemberService;
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
	private final ReservationService reservationService;
	private final MemberService memberService;

	//좌석o 선택페이지 이동
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

	//좌석o 예매하기
	@PostMapping(value = "/add")
	public String addreservation(Model model, @RequestParam("anum") int anum, @RequestParam("seat1") String seat1,
			@RequestParam("seat") String seat, @RequestParam("cnt") int cnt, @RequestParam("price") int price,
			Principal principal, UpdateDto updateDto) throws Exception {

		Application application = applicationService.getApplicationDtl1(anum);

		String name = principal.getName();

		seatService.updateSeat(updateDto, anum, seat1);

		reservationService.saveReservation(application, name, seat, cnt, price);

		return "redirect:/";
	}

	//좌석x 예매페이지 이동
	@PostMapping(value = "/new1")
	public String reservation1(Model model, @RequestParam("test") long anum) {
		Application application = applicationService.getApplicationDtl1(anum);
		model.addAttribute("application", application);
		model.addAttribute("anum", anum);

		return "reservation/reservationForm";
	}

	//좌석x 예매
	@PostMapping(value = "/add1")
	public String addreservation1(Model model, @RequestParam("anum") int anum, @RequestParam("cnt") int cnt,
			@RequestParam("price") int price, Principal principal) throws Exception {

		Application application = applicationService.getApplicationDtl1(anum);

		String name = principal.getName();

		String seat = null;

		reservationService.saveReservation(application, name, seat, cnt, price);

		return "redirect:/";
	}
	
	//예매 삭제
	@GetMapping(value = "/delete/{num}")
	public String reservationDelete(@PathVariable Long num) {

		Reservation reservation = reservationService.getDtl(num);

		String seatDetail = reservation.getApplication().getSeatDetail();

		Long deleteSeat = reservation.getApplication().getNum();
		int anum = Long.valueOf(deleteSeat).intValue();

		if (seatDetail != null) {
			String selectSeat = reservation.getSeat();
			String[] array = selectSeat.split(",");

			seatService.cancelUpdateSeat(seatDetail, anum, array);
		}
		
		reservationService.deleteReservation(num);

		return "redirect:/admin/reservationMng";
	}
	
	//내 예매내역 호출
    @GetMapping(value="/myReservation")
    public String myReservation(Model model, Principal principal) {
    	
    	String id = principal.getName();
    	Member member = memberService.findUserMyPage(id);
    	List<Reservation> reservations = reservationService.getReservationList(member.getNum());
    	
    	if(reservations.isEmpty()) {
    		model.addAttribute("reservations", "nothing");
    	
    	}else {
    		model.addAttribute("reservations", reservations);
    	}
    	
    	return "reservation/myReservation";
    }
    
    //예매내역 취소
    @PostMapping(value="/cancelMyReservation/{num}")
    public String cancelMyReservation(@PathVariable Long num) {
    	
    	Reservation reservation = reservationService.getDtl(num);

		String seatDetail = reservation.getApplication().getSeatDetail();

		Long deleteSeat = reservation.getApplication().getNum();
		int anum = Long.valueOf(deleteSeat).intValue();

		if (seatDetail != null) {
			String selectSeat = reservation.getSeat();
			String[] array = selectSeat.split(", ");

			seatService.cancelUpdateSeat(seatDetail, anum, array);
		}
    	
		reservationService.cancleReservation(num);
		
    	return "redirect:/reservations/myReservation";
    }
    
    @PostMapping(value="/refundMyReservation/{num}")
    public String refundMyReservation(@PathVariable Long num) {
    	
    	Reservation reservation = reservationService.getDtl(num);

		String seatDetail = reservation.getApplication().getSeatDetail();

		Long deleteSeat = reservation.getApplication().getNum();
		int anum = Long.valueOf(deleteSeat).intValue();

		if (seatDetail != null) {
			String selectSeat = reservation.getSeat();
			String[] array = selectSeat.split(", ");

			seatService.cancelUpdateSeat(seatDetail, anum, array);
		}
		
		reservationService.refundReservation(num);
		
    	return "redirect:/reservations/myReservation";
    }
    
   
}
