package com.asia.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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

		String oriname = attachFile.getOriginalFilename();
		String attachName = "";
		String attachUrl = "";

		if (!StringUtils.isEmpty(oriname)) {
			attachName = fileService.uploadFile(attachLocation, oriname, attachFile.getBytes());
			attachUrl = "/images/item" + attachName;
		}

		attach.updateAttach(oriname, attachName, attachUrl);
		attachRepository.save(attach);
	}

	public void updateAttach(Long num, MultipartFile attachFile) throws Exception {

		if (!attachFile.isEmpty()) {
			Attach savedAttach = attachRepository.findById(num)
					.orElseThrow(EntityNotFoundException::new);
			
			if (!StringUtils.isEmpty(savedAttach.getName())) {
			
				fileService.deleteFile(attachLocation + "/" + savedAttach.getName());
			}

			String oriname = attachFile.getOriginalFilename();
			String attachName = fileService.uploadFile(attachLocation, oriname, attachFile.getBytes());
			
			String attachUrl = "/images/item/" + attachName;
			savedAttach.updateAttach(oriname, attachName, attachUrl);
		}
	}

}
