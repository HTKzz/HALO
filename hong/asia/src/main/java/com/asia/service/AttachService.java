package com.asia.service;

import javax.persistence.EntityNotFoundException;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

		if (!StringUtils.isEmpty(oriName)) {
			name = fileService.uploadFile(attachLocation, oriName, attachFile.getBytes());
			url = "/shop/item/" + name;
		}

		attach.updateAttach(oriName, name, url);
		attachRepository.save(attach);
	}

	public void updateAttach(Long attachId, MultipartFile attachFile) throws Exception {
		
		if (!attachFile.isEmpty()) {
			Attach savedAttach = attachRepository.findById(attachId).orElseThrow(EntityNotFoundException::new);
			
			if (!StringUtils.isEmpty(savedAttach.getName())) {
				fileService.deleteFile(attachLocation + "/" + savedAttach.getName());
			}
			
			String oriName = attachFile.getOriginalFilename();
			String name = fileService.uploadFile(attachLocation, oriName, attachFile.getBytes());
			String url = "/images/item/" + name;
			savedAttach.updateAttach(oriName, name, url);
		}
	}
	
}
