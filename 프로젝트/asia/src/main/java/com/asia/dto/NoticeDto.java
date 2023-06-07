package com.asia.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.asia.entity.Member;
import com.asia.entity.Notice;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class NoticeDto{
	
	private String name;
	
	private String content;
	
	private LocalDate d_date;
	
	private Integer cnt;
	
	private Long num;
	
	private String createdBy;
    
    private Long allNoticeCnt;
    
    private String prevContent;
    
    private String nextContent;
	
	private Member member;
	
	private LocalDateTime regTime;
	
	private String not;
	
	private List<AttachDto> attachDtoList = new ArrayList<>();
	
	private List<Long> attachNums = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Notice createNotice() {
		return modelMapper.map(this, Notice.class);
	}
	
	public static NoticeDto of(Notice notice) {
		return modelMapper.map(notice, NoticeDto.class);
	}
	
	@QueryProjection
	public NoticeDto(Long num) {
		this.num = num;
	}
}