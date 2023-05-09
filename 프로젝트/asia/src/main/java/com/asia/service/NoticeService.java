package com.asia.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asia.dto.NoticeFormDto;
import com.asia.entity.Notice;
import com.asia.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {
	
	private final NoticeRepository noticeRepository;
	
	
	//새글등록
	public Notice saveNotice(NoticeFormDto noticeFormDto) throws Exception{
		 Notice notice = noticeFormDto.createNotice();
		
		return noticeRepository.save(notice);
	}
	
	
	//새글등록
//		public Long saveNotice(NoticeFormDto noticeFormDto) throws Exception{
//			 Notice notice = noticeFormDto.createNotice();
//			 noticeRepository.save(notice);
//			return null;
//		}

}
