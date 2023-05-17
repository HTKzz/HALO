package com.asia.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

		String oriname = attachFile.getOriginalFilename();
		String name = "";
		String url = "";

		if (!StringUtils.isEmpty(oriname)) {
			name = fileService.uploadFile(attachLocation, oriname, attachFile.getBytes());
			url = "/asia/attach/" + name;
		}

		attach.updateAttach(oriname, name, url);
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
			
			String name = fileService.uploadFile(attachLocation, oriname, attachFile.getBytes());
			
			String url = "/asia/attach/" + name;
			
			savedAttach.updateAttach(oriname, name, url);
		}
	}
	
	public void deleteAttach(Long num) throws Exception{
		
		List<AttachDto> attachList = attachRepository.getAttachLists(num);
		
		for(int i=0 ; i<attachList.size() ; i++) {
			
			fileService.deleteFile(attachLocation + "/" + attachList.get(i).getName());
		}
		
		
			
	}

}
