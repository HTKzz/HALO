package com.asia.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asia.dto.ReservationSearchDto;
import com.asia.entity.Reservation;
import com.asia.service.ApplicationService;
import com.asia.service.ReservationService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class AdminController {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ApplicationService ApplicationService;
	private final ReservationService reservationService;
	
	@GetMapping(value = "/reservationMng")
	public String reservationManage(ReservationSearchDto reservationSearchDto, @PageableDefault(page=0, size=10, sort="num", direction=Sort.Direction.DESC) Pageable pageable, Model model) {
				
		Page<Reservation> reservations = reservationService.getAdminReservationPage(reservationSearchDto, pageable);
		
	    int nowPage = reservations.getPageable().getPageNumber() + 1 ;
	    int startPage =  Math.max(nowPage - 4, 1);
	    int endPage = Math.min(nowPage+9, reservations.getTotalPages());
	    model.addAttribute("nowPage", nowPage);
	    model.addAttribute("startPage", startPage);
	    model.addAttribute("endPage", endPage);
		
		model.addAttribute("reservations", reservations);
		model.addAttribute("reservationSearchDto", reservationSearchDto);
		
		return "admin/reservationMng";
	}
}
