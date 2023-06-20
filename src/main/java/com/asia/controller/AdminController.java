package com.asia.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.asia.dto.SearchDto;
import com.asia.entity.Application;
import com.asia.entity.Member;
import com.asia.entity.Reservation;
import com.asia.service.ApplicationService;
import com.asia.service.MemberService;
import com.asia.service.ReservationService;
import com.asia.service.SeatService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class AdminController {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ReservationService reservationService;
	private final ApplicationService applicationService;
	private final SeatService seatService;
	private final MemberService memberService;

	// 예매 관리 페이지 호출
	@GetMapping(value = { "/reservationMng", "/reservationMng/{page}" })
	public String reservationManage(@PathVariable("page") Optional<Integer> page, Model model, SearchDto searchDto) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);

		Page<Reservation> reservations = reservationService.getAdminReservationPage(searchDto, pageable);

		model.addAttribute("maxPage", 10);
		model.addAttribute("reservations", reservations);
		
		if (!searchDto.getSearchQuery().matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝|(|)|.|-]*")) {
			searchDto.setSearchQuery("");
		}
		
		model.addAttribute("SearchDto", searchDto);

		return "admin/reservationMng";
	}
	
	// 예매 관리 취소
	@PostMapping(value = "/adminCancelReservation/{num}")
	public String adminCancelReservation(@PathVariable("num") Long num, Model model, RedirectAttributes re) {

		Reservation reservation = reservationService.getDtl(num);
		
		if(reservation.getApplication() == null) {
			re.addFlashAttribute("errorMessage", "상품정보가 존재하지 않습니다.");
			return "redirect:/admin/reservationMng";
		}

		String seatDetail = reservation.getApplication().getSeatDetail();

		Long deleteSeat = reservation.getApplication().getNum();

		if (seatDetail != null) {
			String selectSeat = reservation.getSeat();
			String[] array = selectSeat.split(", ");

			seatService.cancelUpdateSeat(seatDetail, deleteSeat, array);
		}
		reservationService.cancelReservation(num);

		return "redirect:/admin/reservationMng";
	}
	
	// 예매관리 환불
	@PostMapping(value = "/refundComplete/{num}")
	public String refundComplete(@PathVariable Long num, RedirectAttributes re) {
		
		Reservation reservation = reservationService.getDtl(num);
		
		if(reservation.getApplication() == null) {
			re.addFlashAttribute("errorMessage", "상품정보가 존재하지 않습니다.");
			return "redirect:/admin/reservationMng";
		}

		reservationService.refundComplete(num);

		return "redirect:/admin/reservationMng";
	}

	// 상품관리 페이지 호출, 검색
	@GetMapping(value = { "/applications", "/applications/{page}" })
	public String applicationManageList(@PathVariable("page") Optional<Integer> page, Model model, SearchDto searchDto) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);

		Page<Application> applications = applicationService.getApplicationList(searchDto, pageable);

		model.addAttribute("maxPage", 10);
		model.addAttribute("applications", applications);
		
		if (!searchDto.getSearchQuery().matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝|(|)|.|-]*")) {
			searchDto.setSearchQuery("");
		}
		
		model.addAttribute("SearchDto", searchDto);
		
		return "admin/applicationMng";
	}

	// 상품관리 승인상태 수정
	@GetMapping(value = "/approvalstatus/change/{num}")
	public String approvalstatusChange(@PathVariable("num") Long num, Model model) {

		try {
			Application application = applicationService.getAppDtl(num);
			applicationService.updateApprovalStatus(application);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "프로그램 승인 중 에러가 발생하였습니다.");
			return "admin/applicationMng";
		}

		return "redirect:/admin/applications";
	}

	// 전체회원 리스트 출력
	@GetMapping(value = { "/memberMngList", "/memberMngList/{page}" })
	public String memberMngList(@PathVariable("page") Optional<Integer> page, Model model, SearchDto searchDto) {
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
		Page<Member> memberMngList = memberService.memberList(searchDto, pageable);

		model.addAttribute("maxPage", 10);
		model.addAttribute("memberMngList", memberMngList);
		
		if (!searchDto.getSearchQuery().matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝|(|)|.|-]*")) {
			searchDto.setSearchQuery("");
		}
		
		model.addAttribute("SearchDto", searchDto);

		return "admin/memberMng";
	}
	
	// 회원관리 상태 수정 (일반->블랙)
	@GetMapping(value = "/memberStat/change/{num}")
	public String memberstatChange(@PathVariable("num") Long num) {
		
		Member member = memberService.getMemDtl(num);
		memberService.updateStat(member);
			
		return "redirect:/admin/memberMngList";
	}
}
