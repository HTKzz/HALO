package com.asia.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.asia.dto.ApplicationDto;
import com.asia.dto.AttachDto;
import com.asia.dto.SearchDto;
import com.asia.entity.Application;
import com.asia.entity.Attach;
import com.asia.entity.Member;
import com.asia.entity.SeatA;
import com.asia.entity.SeatB;
import com.asia.entity.SeatC;
import com.asia.repository.ApplicationRepository;
import com.asia.repository.AttachRepository;
import com.asia.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationService {

	private final ApplicationRepository applicationRepository;
	private final MemberRepository memberRepository;
	private final AttachService attachService;
	private final AttachRepository attachRepository;
	private final SeatService seatService;

	public Long saveApplication(ApplicationDto applicationDto, List<MultipartFile> attachFileList, String name)
			throws Exception {

		validateDuplicateApplication(applicationDto);

		Member member = memberRepository.findById(name);

		String seat = applicationDto.getSeatDetail();
		String startdate = applicationDto.getSdate();
		String enddate = applicationDto.getEdate();
		SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");

		Date firstDate = sdfYMD.parse(startdate);
		Date secondDate = sdfYMD.parse(enddate);

		long diff = secondDate.getTime() - firstDate.getTime();
		TimeUnit time = TimeUnit.DAYS;
		long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);

		for (int i = 0; i < diffrence + 1; i++) {
			if (seat.equals("A")) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(firstDate);
				cal.add(Calendar.DATE, i);
				applicationDto.setUdate(sdfYMD.format(cal.getTime()));

				Application application = applicationDto.createApplication();
				application.setMember(member);
				application.setApprovalStatus("미승인");
				applicationRepository.save(application);

				for (int j = 1; j < 49; j++) {
					SeatA seatA = new SeatA();
					seatA.setApplication(application);
					seatA.setSeat("A" + j);

					seatService.saveSeatA(seatA);
				}

				for (int j = 0; j < attachFileList.size(); j++) {
					Attach attach = new Attach();
					attach.setApplication(application);

					if (j == 0)
						attach.setThumb("Y");
					else
						attach.setThumb("N");

					attachService.saveAttach(attach, attachFileList.get(j));
				}
			}

			if (seat.equals("B")) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(firstDate);
				cal.add(Calendar.DATE, i);
				applicationDto.setUdate(sdfYMD.format(cal.getTime()));

				Application application = applicationDto.createApplication();
				application.setMember(member);
				application.setApprovalStatus("미승인");
				applicationRepository.save(application);

				for (int j = 1; j < 41; j++) {
					SeatB seatB = new SeatB();
					seatB.setApplication(application);
					seatB.setSeat("A" + j);

					seatService.saveSeatB(seatB);
				}

				for (int j = 0; j < attachFileList.size(); j++) {
					Attach attach = new Attach();
					attach.setApplication(application);

					if (j == 0)
						attach.setThumb("Y");
					else
						attach.setThumb("N");

					attachService.saveAttach(attach, attachFileList.get(j));
				}
			}

			if (seat.equals("C")) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(firstDate);
				cal.add(Calendar.DATE, i);
				applicationDto.setUdate(sdfYMD.format(cal.getTime()));

				Application application = applicationDto.createApplication();
				application.setMember(member);
				application.setApprovalStatus("미승인");
				applicationRepository.save(application);

				for (int j = 1; j < 33; j++) {
					SeatC seatC = new SeatC();
					seatC.setApplication(application);
					seatC.setSeat("A" + j);

					seatService.saveSeatC(seatC);
				}

				for (int j = 0; j < attachFileList.size(); j++) {
					Attach attach = new Attach();
					attach.setApplication(application);

					if (j == 0)
						attach.setThumb("Y");
					else
						attach.setThumb("N");

					attachService.saveAttach(attach, attachFileList.get(j));
				}
			}

			if (seat.equals("")) {

				Calendar cal = Calendar.getInstance();
				cal.setTime(firstDate);
				cal.add(Calendar.DATE, i);
				applicationDto.setUdate(sdfYMD.format(cal.getTime()));

				Application application = applicationDto.createApplication();
				application.setMember(member);
				application.setApprovalStatus("미승인");
				applicationRepository.save(application);

				for (int j = 0; j < attachFileList.size(); j++) {
					Attach attach = new Attach();
					attach.setApplication(application);

					if (j == 0)
						attach.setThumb("Y");
					else
						attach.setThumb("N");

					attachService.saveAttach(attach, attachFileList.get(j));
				}
			}
		}

		return null;

	}

	// 프로그램 신청글 상세보기페이지 호출
	@Transactional(readOnly = true)
	public ApplicationDto getApplicationDtl(Long num) {
		
		ApplicationDto appDto = applicationRepository.findByNum(num);
		
		List<Application> application1 = applicationRepository.findByName(appDto.getName());

		List<Attach> attachList = attachRepository.findByApplicationNumOrderByNumAsc(application1.get(0).getNum()); // 해당 프로그램 이미지 조회
		List<AttachDto> attachDtoList = new ArrayList<>();
		for (Attach attach : attachList) {
			AttachDto attachDto = AttachDto.of(attach);
			attachDtoList.add(attachDto);
		}

		// 프로그램 아이디를 통해서 프로그램 엔티티를 조회한다. 존해하지 않을때에는 예외를 발생시킴.
		Application application = applicationRepository.findById(application1.get(0).getNum())
					.orElseThrow(EntityNotFoundException::new);
		ApplicationDto applicationDto = ApplicationDto.of(application);
		applicationDto.setAttachDtoList(attachDtoList);
		return applicationDto;
	}

	@Transactional(readOnly = true)
	public ApplicationDto getApplicationDtlModify(Long num) {
		List<Attach> attachList = attachRepository.findByApplicationNumOrderByNumAsc(num); // 해당 프로그램 이미지 조회
		List<AttachDto> attachDtoList = new ArrayList<>();
		for (Attach attach : attachList) {
			AttachDto attachDto = AttachDto.of(attach);
			attachDtoList.add(attachDto);
		}

		// 프로그램 아이디를 통해서 프로그램 엔티티를 조회한다. 존해하지 않을때에는 예외를 발생시킴.
		Application application = applicationRepository.findById(num).orElseThrow(EntityNotFoundException::new);
		ApplicationDto applicationDto = ApplicationDto.of(application);
		applicationDto.setAttachDtoList(attachDtoList);
		return applicationDto;
	}

	public Long updateApplication(ApplicationDto applicationDto, List<MultipartFile> attachFileList) throws Exception {
		// 프로그램 수정
		Application application = applicationRepository.findById(applicationDto.getNum())
				.orElseThrow(EntityNotFoundException::new);

		List<Application> appList = applicationRepository.findByName(application.getName());

		for (int i = 0; i < appList.size(); i++) {
			Application application1 = appList.get(i);
			application1.updateApplication(applicationDto);
			List<AttachDto> AttachList = attachRepository.findByApplicationNum(appList.get(i).getNum());

			// 첨부파일 수정
			for (int j = 0; j < attachFileList.size(); j++) {
				attachService.updateAttach(AttachList.get(j).getNum(), attachFileList.get(j));
			}
		}
		return application.getNum();
	}

	public Page<ApplicationDto> getList1(Pageable pageable, String programCategory) {
		
		List<Long> appNum = applicationRepository.getAppNum(programCategory);
		List<Application> appList = applicationRepository.findByNums(appNum);
		
		List<ApplicationDto> list = new ArrayList<>();
		for(Application app: appList) {
			list.add(ApplicationDto.of(app));
		}
		
		List<ApplicationDto> resultList = new ArrayList<ApplicationDto>();
		int offset = Long.valueOf(pageable.getOffset()).intValue();
		int offset2 = pageable.getPageSize();
		
		for (int x = offset; x < offset + offset2; x++) {
			if (x < list.size()) {
				List<AttachDto> attach = attachRepository.getAppList(list.get(x).getNum());
				list.get(x).setUrl(attach.get(0).getUrl());
				resultList.add(list.get(x));
			}
		}

		return new PageImpl<>(resultList, pageable, list.size());
	}

	public List<Application> getApplication(String Name) {
		List<Application> application = applicationRepository.findByName(Name);

		return application;
	}

	@Transactional(readOnly = true)
	public List<ApplicationDto> getApplicationSelect(String name) {
		List<ApplicationDto> list = applicationRepository.getList2(name);
		return list;
	}

	public Application getAppDtl(long anum) {
		Application application = applicationRepository.getApplication(anum);
		return application;
	}

	// 승인상태 수정
	public void updateApprovalStatus(Application application) throws Exception {
		List<Application> appList = applicationRepository.findByName(application.getName());

		for (int i = 0; i < appList.size(); i++) {
			Application application1 = appList.get(i);
			applicationRepository.updateApprovalStatus(application1.getNum());
		}
	}
	
	// 메인페이지 슬라이드 사진 불러오기
	@Transactional(readOnly = true)
	public List<ApplicationDto> getSlideList() {
		List<Long> listNum = applicationRepository.getAppNumNoPC();
		List<Application> appList = applicationRepository.findByNums(listNum);
		
		List<ApplicationDto> list = new ArrayList<>();
		for(Application app: appList) {
			list.add(ApplicationDto.of(app));
		}
		
		int size = list.size();
		
		for(int i = 0; i < size; i++) {
			if(i <= 9) {
				List<Application> application = applicationRepository.findByName(list.get(i).getName());
				List<AttachDto> attach = attachRepository.getAppList(application.get(0).getNum());
				list.get(i).setUrl(attach.get(1).getUrl());
			} else {
				list.remove(10);
			}
		}
		
		System.out.println(list.size());
		
		return list;
	}
	
	public Page<Application> getApplicationList(SearchDto searchDto, Pageable pageable) {
		return applicationRepository.getApplicationList(searchDto, pageable);
	}
	
	public void deleteApplication(Long num) {
		applicationRepository.deleteByNum(num);
	}
	
	private void validateDuplicateApplication(ApplicationDto applicationDto) {
		List<Application> findApplication = applicationRepository.findByName(applicationDto.getName());
		
		if (findApplication.size() != 0) {
			throw new IllegalStateException("이미 등록된 제목입니다."); // 이미 만들어진 이름의 경우 예외를 발생시킨다.
		}
	}

}
