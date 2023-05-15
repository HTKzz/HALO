package com.asia.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asia.dto.ApplicationDto;
import com.asia.entity.Application;
import com.asia.entity.Member;
import com.asia.entity.SeatA;
import com.asia.entity.SeatB;
import com.asia.entity.SeatC;
import com.asia.repository.ApplicationRepository;
import com.asia.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationService {

	private final ApplicationRepository applicationRepository;
	private final MemberRepository memberRepository;
	private final SeatService seatService;
	

	public Long saveApplication(ApplicationDto applicationDto, String name) throws Exception {
		
		Member member = memberRepository.findById(name);
		
		System.out.println(member);
		
		String seat = applicationDto.getSeatDetail();
		String startdate = applicationDto.getStartdate();
		String enddate = applicationDto.getEnddate();
		SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
		
		Date firstDate = sdfYMD.parse(startdate);
		Date secondDate = sdfYMD.parse(enddate);
		
		long diff = secondDate.getTime() - firstDate.getTime();
		TimeUnit time = TimeUnit.DAYS;
		long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);
		
		
		for (int i = 0; i < diffrence+1; i++) {
			if (seat.equals("A")) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(firstDate);
				cal.add(Calendar.DATE, i);
				applicationDto.setUdate(sdfYMD.format(cal.getTime()));
				
				Application application = applicationDto.createApplication();
				application.setMember(member);
				System.out.println(application);
				applicationRepository.save(application);
				
				for (int j = 1; j < 49; j++) {
					SeatA seatA = new SeatA();
					seatA.setApplication(application);
					seatA.setSeat("A"+j);

					seatService.saveSeatA(seatA);
				}
			}
			
			if (seat.equals("B")) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(firstDate);
				cal.add(Calendar.DATE, i);
				applicationDto.setUdate(sdfYMD.format(cal.getTime()));
				
				Application application = applicationDto.createApplication();
				application.setMember(member);
				System.out.println(application);
				applicationRepository.save(application);
				
				for (int j = 1; j < 41; j++) {
					SeatB seatB = new SeatB();
					seatB.setApplication(application);
					seatB.setSeat("A"+j);

					seatService.saveSeatB(seatB);
				}
			}
			
			if (seat.equals("C")) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(firstDate);
				cal.add(Calendar.DATE, i);
				applicationDto.setUdate(sdfYMD.format(cal.getTime()));
				
				Application application = applicationDto.createApplication();
				application.setMember(member);
				System.out.println(application);
				applicationRepository.save(application);
				
				for (int j = 1; j < 33; j++) {
					SeatC seatC = new SeatC();
					seatC.setApplication(application);
					seatC.setSeat("A"+j);

					seatService.saveSeatC(seatC);
				}
			}
			
			if (seat == "") {
				Calendar cal = Calendar.getInstance();
				cal.setTime(firstDate);
				cal.add(Calendar.DATE, i);
				applicationDto.setUdate(sdfYMD.format(cal.getTime()));
				
				Application application = applicationDto.createApplication();
				application.setMember(member);
				System.out.println(application);
				applicationRepository.save(application);
			}
		}
		return null;
		
//		return Application.getNum();
	}
	
//	public Page<Application> findAll(Pageable pageable){
//		return applicationRepository.findAll(pageable);
//	}
//	
//	public Page<ApplicationDto> getList(ApplicationDto applicationDto, Pageable pageable){
//		return applicationRepository.getList(applicationDto, pageable);
//	}
	
	public Page<ApplicationDto> getList1(ApplicationDto applicationDto, Pageable pageable){
		List<ApplicationDto> list =  applicationRepository.getList1();
		List<ApplicationDto> resultList = new ArrayList<ApplicationDto>();
		int offset = Long.valueOf(pageable.getOffset()).intValue();
		for(int x=offset; x<=offset+9; x++) {
			if(x<list.size()) {
				resultList.add(list.get(x));
			}
		}
		
		return new PageImpl<>(resultList, pageable, list.size());
	}
	
	@Transactional(readOnly = true)
	public List<ApplicationDto> getApplicationDtl(String name) {
//		List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
//		List<ItemImgDto> itemImgDtoList = new ArrayList<>();
//		for (ItemImg itemImg : itemImgList) {
//			ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
//			itemImgDtoList.add(itemImgDto);
//		}
		
		List<ApplicationDto> list = applicationRepository.getApplication(name);
//		ApplicationDto applicationDto = ApplicationDto.of(application);
//		applicationDto.setItemImgDtoList(itemImgDtoList);
		return list;
	}
	
	@Transactional(readOnly = true)
	public List<ApplicationDto> getApplicationSelect(String name) {
		List<ApplicationDto> list = applicationRepository.getList2(name);
		return list;
	}

	public Application getApplicationDtl1(long anum) {
		Application application = applicationRepository.findByNum(anum);
		return application;
	}
}
