package com.asia.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.asia.dto.ApplicationDto;
import com.asia.dto.CountDto;
import com.asia.entity.Application;
import com.asia.service.ApplicationService;
import com.asia.service.AttachService;
import com.asia.service.SeatService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/board")
@Controller
@RequiredArgsConstructor
public class ApplicationController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ApplicationController.class);

	private final ApplicationService applicationService;
	private final SeatService seatService;
	private final AttachService attachService;

	// 프로그램 신청 페이지 호출
	@GetMapping(value = "/program/apply")
	public String applicationForm(Model model, Principal principal) {
		model.addAttribute("applicationDto", new ApplicationDto());
		return "board/program/applicationForm";
	}

	// 프로그램 신청 및 저장
	@PostMapping(value = "/program/apply")
	public String programApply(Principal principal, @Valid ApplicationDto applicationDto, BindingResult bindingResult, Model model,
							   @RequestParam("attachFile") List<MultipartFile> attachFileList) {
		
		if (bindingResult.hasErrors()) {   // 상품 등록시 필수 값이 없다면 다시 상품 등록 페이지로 전환한다.
			return "board/program/applicationForm";
		}
		
		if (attachFileList.get(0).isEmpty() && applicationDto.getNum() == null) {
			model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
			return "board/program/applicationForm";   // 상품 등록시 첫 번째 이미지가 없다면 에러 메시지와 함께 상품등록 페이지로 전환한다.
		}   // 상품 첫번째 이미지는 메인 페이지에서 보여줄 상품 이미지를 사용하기 위해 필수 값으로 지정한다.
		
		try {
			String name = principal.getName();
			applicationService.saveApplication(applicationDto, attachFileList, name);   // 상품 저장 로직을 호출. 상품정보와 상품이미지정보를 넘긴다.
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "board/program/applicationForm";
		}
		
		return "redirect:/";   // 정상적으로 등록되었다면 메인페이지로 이동한다.
		
	}
	
	// 프로그램 수정 페이지 호출
	@GetMapping(value = "/program/apply/{num}")
	public String applicationModify(@PathVariable("num") Long num, Model model) {
		
		try {
			ApplicationDto applicationDto = applicationService.getApplicationDtlModify(num);   // 조회한 프로그램 정보를 model에 담아서 뷰로 전달
			model.addAttribute("applicationDto", applicationDto);
		} catch(EntityNotFoundException e) {
			model.addAttribute("errorMessage", "존재하지 않는 프로그램입니다.");
			model.addAttribute("applicationDto", new ApplicationDto());
			return "board/program/applicationForm";
		}
		
		return "board/program/applicationForm";
	}
	
	// 프로그램 수정 후 저장
	@PostMapping(value = "/program/apply/{num}")
	public String applicationUpdate(@Valid ApplicationDto applicationDto, BindingResult bindingResult,
									@RequestParam("attachFile") List<MultipartFile> attachFileList, Model model) {
		if(bindingResult.hasErrors()) {
			return "board/program/applicationForm";
		}
		
		if(attachFileList.get(0).isEmpty() && applicationDto.getNum() == null) {
			model.addAttribute("errorMessage", "첫번째 프로그램 이미지는 필수 입력 값입니다.");
			return "board/program/applicationForm";
		}
		
		try {
			applicationService.updateApplication(applicationDto, attachFileList);
			if(applicationDto.getProgramCategory().equals("공연")) {
				return "redirect:/board/program/showlist";
			} else if(applicationDto.getProgramCategory().equals("전시")) {
				return "redirect:/board/program/exhibitionlist";
			} else {
				return "redirect:/board/program/eventlist";
			}
			
		} catch (Exception e) {
			model.addAttribute("errorMessage", "프로그램 신청 수정 중 에러가 발생하였습니다.");
			return "board/program/applicationForm";
		}
	}
	
	// 프로그램 신청 상세보기 호출
	@GetMapping(value = "/program/application/{num}")
	public String applicationDtl(Model model, @PathVariable long num) {
		ApplicationDto applicationDto = applicationService.getApplicationDtl(num);
		model.addAttribute("application2", applicationDto);
		
		List<ApplicationDto> application1 = applicationService.getApplicationSelect(applicationDto.getName());
		String seatDetail = applicationDto.getSeatDetail();

		Map<Long, Long> seatMap = new HashMap<>();
		Map<String, String> seatMap1 = new HashMap<>();
	
		if (seatDetail == null) {
			for(ApplicationDto dto : application1) {
				String total = dto.getUdate();
				seatMap1.put(total, Long.toString(dto.getNum()));
			}
			
			Map<String, String> sortedMap = new TreeMap<>(seatMap1);
			model.addAttribute("select", sortedMap);
			
			return "board/program/applicationDtl1";
		}
		
		if (seatDetail.equals("A")) {
			List<CountDto> seat1 = seatService.getCountA();
			for(CountDto dto : seat1) {
				seatMap.put(dto.getApplication_id(), dto.getCount());
			}
		}
		
		if (seatDetail.equals("B")) {
			List<CountDto> seat1 = seatService.getCountB();
			for(CountDto dto : seat1) {
				seatMap.put(dto.getApplication_id(), dto.getCount());
			}
		}
		
		if (seatDetail.equals("C")) {
			List<CountDto> seat1 = seatService.getCountC();
			for(CountDto dto : seat1) {
				seatMap.put(dto.getApplication_id(), dto.getCount());
			}
		}
	
		for(ApplicationDto dto : application1) {
			long count = 0;
			if(seatMap.containsKey(dto.getNum())) {
				count = seatMap.get(dto.getNum());
			}
			String total = dto.getUdate() + "일 남은 좌석 : " + Long.toString(count);
			seatMap1.put(total, Long.toString(dto.getNum()));
		}
		
		Map<String, String> sortedMap = new TreeMap<>(seatMap1);
		model.addAttribute("select", sortedMap);
		
		return "board/program/applicationDtl";
	}
	
	// 프로그램 신청 삭제하기 
   	@GetMapping(value="/program/delete/{num}")
	public String applicationDelete(@PathVariable Long num, Model model) throws Exception {
   		
   		Application application = applicationService.getAppDtl(num);
   		List<Application> list = applicationService.getApplication(application.getName());
   		
   		for(int i = 0; i < list.size(); i++) {
   	   		attachService.appDeleteAttach(list.get(i).getNum());
			applicationService.deleteApplication(list.get(i).getNum());
   		}
		
   		return "redirect:/admin/applications";
	}
    
    // 카테고리별 게시판 호출(공연)
    @GetMapping("/program/showlist")
    public String showListView(Model model, Long num,
    						   @PageableDefault(page = 0, size = 6)Pageable pageable) {
    	
            //큰카테고리에 해당하는 상품 가져오기.
    		String programCategory = "공연";
    		
            Page<ApplicationDto> showapplications = applicationService.getList1(pageable, programCategory);
            
            int nowPage = showapplications.getPageable().getPageNumber() + 1; //pageable에서 넘어온 현재페이지를 가지고올수있다 * 0부터시작하니까 +1
            int startPage = Math.max(nowPage - 4, 1); //매개변수로 들어온 두 값을 비교해서 큰값을 반환
            int endPage = Math.min(nowPage + 4, showapplications.getTotalPages());
            
            model.addAttribute("application4", showapplications);
            
            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            
            return "board/program/showList";
    	
    }
    
    // 카테고리별 게시판 호출(전시)
    @GetMapping("/program/exhibitionlist")
    public String exhibitionListView(Model model, Long num, String programCategory,
    								 @PageableDefault(page = 0, size = 6)Pageable pageable) {
    	
    	
            //큰카테고리에 해당하는 상품 가져오기.
    		programCategory = "전시";

            Page<ApplicationDto> showapplications = applicationService.getList1(pageable, programCategory);
            
            int nowPage = showapplications.getPageable().getPageNumber() + 1; //pageable에서 넘어온 현재페이지를 가지고올수있다 * 0부터시작하니까 +1
            int startPage = Math.max(nowPage - 4, 1); //매개변수로 들어온 두 값을 비교해서 큰값을 반환
            int endPage = Math.min(nowPage + 4, showapplications.getTotalPages());
            model.addAttribute("application5", showapplications);
            
            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            
            return "board/program/exhibitionList";
    	
    }
    
    // 카테고리별 게시판 호출(행사)
    @GetMapping("/program/eventlist")
    public String eventListView(Model model, Long num, String programCategory,
    							@PageableDefault(page = 0, size = 6)Pageable pageable) {
    	
            //큰카테고리에 해당하는 상품 가져오기.
    		programCategory = "행사";

            Page<ApplicationDto> showapplications = applicationService.getList1(pageable, programCategory);
            
            int nowPage = showapplications.getPageable().getPageNumber() + 1; //pageable에서 넘어온 현재페이지를 가지고올수있다 * 0부터시작하니까 +1
            int startPage = Math.max(nowPage - 4, 1); //매개변수로 들어온 두 값을 비교해서 큰값을 반환
            int endPage = Math.min(nowPage + 4, showapplications.getTotalPages());
            model.addAttribute("application6", showapplications);
            
            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            
            return "board/program/eventList";
    	
    }
}
