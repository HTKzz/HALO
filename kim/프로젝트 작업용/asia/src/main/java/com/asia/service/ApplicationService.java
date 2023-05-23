package com.asia.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.asia.dto.ApplicationDto;
import com.asia.dto.AttachDto;
import com.asia.entity.Application;
import com.asia.entity.Attach;
import com.asia.repository.ApplicationRepository;
import com.asia.repository.AttachRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationService {

	private final ApplicationRepository applicationRepository;
	private final AttachService attachService;
	private final AttachRepository attachRepository;

	public Long saveApplication(ApplicationDto applicationDto, List<MultipartFile> attachFileList) throws Exception {

		// 상품 등록
		Application application = applicationDto.createApplication(); // 프로그램 등록 폼으로부터 입력받은 데이터를 application객체를 생성한다.
		applicationRepository.save(application); // 프로그램 데이터를 저장.

		// 첨부파일 등록(이미지)
		for (int i = 0; i < attachFileList.size(); i++) {
			Attach attach = new Attach();
			attach.setApplication(application);
			if (i == 0) // 첫 번째 이미지일 경우 대표 상품 이미지 여부 값을 Y로 세팅한다. 나머지 상품 이미지는 N으로 설정한다.
				attach.setRepimgYn("Y");
			else
				attach.setRepimgYn("N");
			attachService.saveAttach(attach, attachFileList.get(i)); // 상품 이미지 정보를 저장한다.
		}

		return application.getNum();

	}

	@Transactional(readOnly = true)
	public ApplicationDto getApplicationDtl(Long num) {
		System.out.println(num);
//		List<Attach> attachList = attachRepository.findByNumOrderByNumAsc(num);   // 해당 프로그램 이미지 조회
		List<Attach> attachList = attachRepository.findByApplicationNumOrderByNumAsc(num);   // 해당 프로그램 이미지 조회
		System.out.println(attachList);
		List<AttachDto> attachDtoList = new ArrayList<>();
		for (Attach attach : attachList) {
			AttachDto attachDto = AttachDto.of(attach);
			attachDtoList.add(attachDto);
		}

		// 프로그램 아이디를 통해서 프로그램 엔티티를 조회한다. 존해하지 않을때에는 예외를 발생시킴.
		Application application = applicationRepository.findById(num)
				.orElseThrow(EntityNotFoundException::new);
		ApplicationDto applicationDto = ApplicationDto.of(application);
		applicationDto.setAttachDtoList(attachDtoList);
		return applicationDto;
	}
	
	public Long updateApplication(ApplicationDto applicationDto, List<MultipartFile> attachFileList) throws Exception {		
		// 프로그램 수정
		Application application = applicationRepository.findById(applicationDto.getNum())
				.orElseThrow(EntityNotFoundException::new);
		application.updateApplication(applicationDto);
		List<Long> attachIds = applicationDto.getAttachIds();
		
		// 첨부파일 수정
		for(int i = 0; i < attachFileList.size(); i++) {
			attachService.updateAttach(attachIds.get(i),
					attachFileList.get(i));
		}
		return application.getNum();
	}
	
//	@Transactional(readOnly = true)
//	public Page<Application> getApplicationPage(ApplicationSearchDto applicationSearchDto, Pageable pageable) {
//		return applicationRepository.getApplicationPage(applicationSearchDto, pageable);
//	}
	
	// 프로그램 신청글 리스트 페이징처리
	//findAll : Application 이라는 클래스가 담긴 List를 반환하는것을 확인할수있다
	public Page<Application> applicationList(Pageable pageable) {
		return applicationRepository.findAll(pageable);
	}
	// 검색기능
	public Page<Application> applicationSearchList(String searchKeyword, Pageable pageable){
		return applicationRepository.findByNameContaining(searchKeyword, pageable);
	}
	public Page<Application> applicationCategorySearchList(String searchKeyword, Pageable pageable){
		return applicationRepository.findByNameContaining(searchKeyword, pageable);
	}

	public void deleteApplication(Long num) {
		applicationRepository.deleteByNum(num);
	}
	
	
}
