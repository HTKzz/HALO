package com.asia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asia.dto.NoticeSearchDto;
import com.asia.entity.Notice;

public interface NoticeRepositoryCustom{
	
	Page<Notice> getNoticeList(NoticeSearchDto noticeSearchDto, Pageable pageable);
}
