package com.asia.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.asia.entity.Attach;
import com.asia.service.AttachService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FileController {
	
	private final AttachService attachService;
    
    // VOC 첨부파일 다운로드
    @GetMapping("/voc/download/{num}")
	public ResponseEntity<Object> vocDownload(@PathVariable("num") Long num) throws Exception {

		Attach file1 = attachService.download(num);
		String fileName = file1.getName();

		String path = "C:/asia/attach/" + fileName;

		try {
			Path filePath = Paths.get(path);
			Resource resource = new InputStreamResource(Files.newInputStream(filePath)); // 파일 resource 얻기

			File file = new File(path);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getName()).build()); // 다운로드
																														// 헤더

			return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
    
    // NOTICE 첨부파일 다운로드
    @GetMapping("/download/{num}")
	public ResponseEntity<Object> download(@PathVariable("num") Long num) throws Exception {
		
		
		Attach file1 = attachService.download(num);
		String fileName = file1.getName();
		
		String path = "C:/asia/attach/" + fileName;
		
		try {
			Path filePath = Paths.get(path);
			Resource resource = new InputStreamResource(Files.newInputStream(filePath)); // 파일 resource 얻기
			
			File file = new File(path);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getName()).build());  // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더
			
			return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
		
		} catch(Exception e) {
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
}
