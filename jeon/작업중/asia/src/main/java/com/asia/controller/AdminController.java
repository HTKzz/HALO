package com.asia.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asia.constant.Stat;
import com.asia.dto.ReservationSearchDto;
import com.asia.entity.Application;
import com.asia.entity.Member;
import com.asia.entity.Reservation;
import com.asia.service.AdminMemberService;
import com.asia.service.ApplicationService;
import com.asia.service.ReservationService;
import com.asia.service.SeatService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class AdminController {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ReservationService reservationService;
	private final AdminMemberService adminMemberService;
	private final ApplicationService applicationService;
	private final SeatService seatService;

	/*
	 * // 예매 관리 페이지
	 * 
	 * @GetMapping(value = "/reservationMng") public String
	 * reservationManage(ReservationSearchDto reservationSearchDto,
	 * 
	 * @PageableDefault(page = 0, size = 10, sort = "num", direction =
	 * Sort.Direction.DESC) Pageable pageable, Model model) {
	 * 
	 * Page<Reservation> reservations =
	 * reservationService.getAdminReservationPage(reservationSearchDto, pageable);
	 * 
	 * int nowPage = reservations.getPageable().getPageNumber() + 1; int startPage =
	 * Math.max(nowPage - 4, 1); int endPage = Math.min(nowPage + 9,
	 * reservations.getTotalPages()); model.addAttribute("nowPage", nowPage);
	 * model.addAttribute("startPage", startPage); model.addAttribute("endPage",
	 * endPage);
	 * 
	 * model.addAttribute("reservations", reservations);
	 * model.addAttribute("reservationSearchDto", reservationSearchDto);
	 * 
	 * return "admin/reservationMng"; }
	 */
	
	// 예매 관리 페이지
	   @GetMapping(value = {"/reservationMng", "/reservationMng/{page}"})
	   public String reservationManage(@PathVariable("page") Optional<Integer> page, Model model, ReservationSearchDto reservationSearchDto) {
	      Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 2);

	      Page<Reservation> reservations = reservationService.getAdminReservationPage(reservationSearchDto, pageable);
	      
	      model.addAttribute("maxPage", 2);
	      model.addAttribute("reservations", reservations);

	      return "admin/reservationMng";
	   }

	// 전체회원 리스트 출력
	@GetMapping(value = "/memberMngList")
	public String memberMngList(Model model,
			@PageableDefault(page = 0, size = 10, sort = "num", direction = Sort.Direction.DESC) Pageable pageable,
			String memberMngSearch) {

		Page<Member> lists = adminMemberService.memberList(pageable);

		model.addAttribute("memberMngList", lists);

		int nowPage = lists.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 9, lists.getTotalPages());
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "admin/memberMng";
	}

	// 회원관리 -> 검색
	@PostMapping(value = "/searchMember")
	public String searchMember(@RequestParam("searchOption") String searchOption,
			@PageableDefault(page = 0, size = 10, sort = "num", direction = Sort.Direction.DESC) Pageable pageable,
			String memberMngSearch, Model model) {

		Page<Member> lists = null;
		if (searchOption.equals("name")) {
			lists = adminMemberService.searchMemberByName(memberMngSearch, pageable);

		} else if (searchOption.equals("tel")) {
			lists = adminMemberService.searchMemberByTel(memberMngSearch, pageable);

		} else if (searchOption.equals("email")) {
			lists = adminMemberService.searchMemberByEmail(memberMngSearch, pageable);

		} else if (searchOption.equals("birth")) {
			lists = adminMemberService.searchMemberByBirth(memberMngSearch, pageable);

		} else if (searchOption.equals("join")) {
			lists = adminMemberService.searchMemberByJoin(memberMngSearch, pageable);

		} else if (searchOption.equals("stat")) {
			Stat stat = Stat.valueOf(memberMngSearch);
			lists = adminMemberService.searchMemberByStat(stat, pageable);

		} else if (searchOption.equals("role")) {
			lists = adminMemberService.searchMemberByRole(memberMngSearch, pageable);

		} else {
			return "admin/memberMng";
		}

		model.addAttribute("memberMngList", lists);

		int nowPage = lists.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 9, lists.getTotalPages());
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "admin/memberMng";
	}

	// 프로그램 신청 리스트 호출(프로그램 신청 관리페이지 호출)
	// 오름차순(ASC), 내림차순(DESC)
	@GetMapping(value = "/applications")
	public String applicationManageList(Model model,
			@PageableDefault(page = 0, size = 10, sort = "num", direction = Sort.Direction.DESC) Pageable pageable,
			String searchKeyword) {

		// 검색기능
		Page<Application> applications = applicationService.applicationList(pageable);

		model.addAttribute("applications", applications);

		int nowPage = applications.getPageable().getPageNumber() + 1; // pageable에서 넘어온 현재페이지를 가지고올수있다 * 0부터시작하니까 +1
		int startPage = Math.max(nowPage - 4, 1); // 매개변수로 들어온 두 값을 비교해서 큰값을 반환
		int endPage = Math.min(nowPage + 4, applications.getTotalPages());

		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "admin/applicationMng";
	}

	// 검색을 이용한 프로그램 신청 리스트 출력(프로그램 관리)
	@PostMapping(value = "/applicationsMngSearch")
	public String applicationManage(Model model,
			@PageableDefault(page = 0, size = 10, sort = "num", direction = Sort.Direction.DESC) Pageable pageable,
			@RequestParam("searchKeyword") String searchKeyword) {

		// 검색기능
		Page<Application> applications = null;
		// searchKeyword = 검색하는 단어
		if (searchKeyword == null) {
			applications = applicationService.applicationList(pageable); // 기존의 리스트보여줌
		} else {
			applications = applicationService.applicationSearchList(searchKeyword, pageable); // 검색리스트반환
		}

		int nowPage = applications.getPageable().getPageNumber() + 1; // pageable에서 넘어온 현재페이지를 가지고올수있다 * 0부터시작하니까 +1
		int startPage = Math.max(nowPage - 4, 1); // 매개변수로 들어온 두 값을 비교해서 큰값을 반환
		int endPage = Math.min(nowPage + 4, applications.getTotalPages());

		model.addAttribute("applications", applications);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "admin/applicationMng";
	}

	// 상품관리 승인상태 수정
	@GetMapping(value = "/approvalstatus/change/{num}")
	public String approvalstatusChange(@PathVariable("num") Long num, Model model) {

		try {
			Application application = applicationService.getApplication(num);
			applicationService.updateApprovalStatus(application);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "프로그램 승인 중 에러가 발생하였습니다.");
			return "admin/applicationMng";
		}

		return "redirect:/admin/applications";
	}

	@PostMapping(value = "/refundComplete/{num}")
	public String refundComplete(@PathVariable Long num) {

		reservationService.refundComplete(num);

		return "redirect:/admin/reservationMng";
	}

	@PostMapping(value = "/adminCancelReservation/{num}")
	public String adminCancelReservation(@PathVariable("num") Long num) {

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

		return "redirect:/admin/reservationMng";
	}
}
