package com.asia.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asia.dto.ApplicationDto;
import com.asia.dto.CountDto;
import com.asia.service.ApplicationService;
import com.asia.service.SeatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ApplicationService applicationService;
	private final SeatService seatService;
	
	//프로그램 게시판페이지 이동
	@GetMapping(value="/application")
	public String application(ApplicationDto applicationDto, Model model, Optional<Integer> page) {
		Pageable pageable = PageRequest.of(page.isPresent()? page.get() : 0 , 10);
		Page<ApplicationDto> list = applicationService.getList1(applicationDto, pageable);
		
        //페이징	        
        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage =  Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage+9, list.getTotalPages());

        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("applicationList", list);
        
        return "application/applicationList";	
	}
	
	//프로그램 상세페이지 이동
	@GetMapping(value="/application/{name}")
	public String applicationDetail(Model model, @PathVariable("name") String name) {
		List<ApplicationDto> application = applicationService.getApplicationDtl(name);
		System.out.println(application);
		model.addAttribute("application", application.get(0));
		
		List<ApplicationDto> application1 = applicationService.getApplicationSelect(name);
		String seatDetail = application.get(0).getSeatDetail();

		Map<Long, Long> seatMap = new HashMap<>();
		Map<String, String> seatMap1 = new HashMap<>();
	
		if (seatDetail == null) {
			for(ApplicationDto dto : application1) {
				String total = dto.getUdate();
				seatMap1.put(total, Long.toString(dto.getNum()));
			}
			
			Map<String, String> sortedMap = new TreeMap<>(seatMap1);
			model.addAttribute("select", sortedMap);
			
			return "application/applicationDetail1";
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
		
		return "application/applicationDetail";
	}
}
