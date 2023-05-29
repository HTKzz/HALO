package com.asia.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.asia.dto.AttachDto;
import com.asia.entity.Attach;
import com.asia.repository.AttachRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AttachService {

	@Value("${attachLocation}")
	private String attachLocation;

	private final AttachRepository attachRepository;
	
	private final FileService fileService;

	public void saveAttach(Attach attach, MultipartFile attachFile) throws Exception {
		String oriName = attachFile.getOriginalFilename();
		String name = "";
		String url = "";

		// 파일 업로드
		if (!StringUtils.isEmpty(oriName)) {
			name = fileService.uploadFile(attachLocation, oriName, attachFile.getBytes());
			// 사용자가 상품 이미지를 등록했다면 저장할 경로, 파일 이름, 파일 바이트수를 파라미터로 하는 uploadFile 매소드를 호출한다.
			url = "/asia/attach/" + name;
			// 저장한 상품 이미지를 불러올 경로를 설정한다. C:/aisa/attach/
		}
		// 상품 이미지 정보 저장
		attach.updateAttach(oriName, name, url); // 업로드했던 상품 이미지 파일의 원래 이름, 실제 로컬에 저장된 상품 이미지 파일의 이름,
		attachRepository.save(attach); // 업로드 결과 로컬에 저장된 상품 이미지 파일을 불러오는 경로 등의 상품 이미지 정보를 저장한다
	}

	public void updateAttach(Long num, MultipartFile attachFile) throws Exception {
		if (!attachFile.isEmpty()) {
			Attach savedAttach = attachRepository.findById(num)
					.orElseThrow(EntityNotFoundException::new);

			// 기존 이미지 파일 삭제
			if (!StringUtils.isEmpty(savedAttach.getName())) {   // 기존에 등록된 프로그램 이미지 파일이 있을 경우 해당파일을 삭제한다.
				fileService.deleteFile(attachLocation + "/" + savedAttach.getName());
			}

			String oriName = attachFile.getOriginalFilename();
			String name = fileService.uploadFile(attachLocation, oriName, attachFile.getBytes());
			String url = "/asia/attach/" + name;
			savedAttach.updateAttach(oriName, name, url);
		}
	}
	
	public void deleteAttach(Long num) throws Exception {
		List<AttachDto> attachLists = attachRepository.getLists(num);

		for (int i = 0; i < attachLists.size(); i++) {

			String attach = attachLists.get(i).getName();

			fileService.deleteFile(attachLocation + "/" + attach);
		}
	}

}
