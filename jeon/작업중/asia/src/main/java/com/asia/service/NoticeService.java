package com.asia.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.asia.dto.AttachDto;
import com.asia.dto.NoticeDto;
import com.asia.dto.NoticeSearchDto;
import com.asia.entity.Attach;
import com.asia.entity.Member;
import com.asia.entity.Notice;
import com.asia.repository.AttachRepository;
import com.asia.repository.MemberRepository;
import com.asia.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeRepository noticeRepository;
	private final MemberRepository memberRepository;
	private final AttachRepository attachRepository;
	private final AttachService attachService;
	
	// 공지글 저장하기.
	public Long writenotice(@Valid NoticeDto noticeDto, List<MultipartFile> attachList, String id) throws Exception {

		Notice notice = noticeDto.createNotice();
		System.out.println(notice);
		
		Member member = memberRepository.findById(id);
		System.out.println(member);
		
		notice.setMember(member);
		notice.setCnt(0);
		
		noticeRepository.save(notice);
		
		LocalDate d_date1 = LocalDate.now(); // 현재 날짜
		notice.setD_date(d_date1);
		
		noticeRepository.save(notice);
		
		for (int i = 0; i < attachList.size(); i++) {
			Attach attach = new Attach();
			attach.setNotice(notice);
			
			if (i == 0) // 첫 번째 이미지일 경우 대표 상품 이미지 여부 값을 Y로 세팅한다. 나머지 상품 이미지는 N으로 설정한다.
				attach.setThumb("Y");
			else
				attach.setThumb("N");
			attachService.saveAttach(attach, attachList.get(i));
		}
		
		return notice.getNum();
	}
	
	// 게시판 답글
	public Long replynotice(@Valid NoticeDto noticeDto, List<MultipartFile> attachList, String id, Model model) throws Exception {
		
		Notice notice = new Notice();		
		notice.setName(noticeDto.getName());
		notice.setContent(noticeDto.getContent());
		notice.setCnt(0);
		
		Member member = memberRepository.findById(id);
		
		notice.setMember(member);
		
		noticeRepository.save(notice);
		
		for (int i = 0; i < attachList.size(); i++) {
			Attach attach = new Attach();
			attach.setNotice(notice);
			
			if (i == 0)
				attach.setThumb("Y");
			else
				attach.setThumb("N");
			attachService.saveAttach(attach, attachList.get(i));
		}

		return notice.getNum();
	}
	
	// 게시판 리스트 싹 다 불러오기 (페이징, 검색)
	public Page<Notice> noticeList(NoticeSearchDto noticeSearchDto, Pageable pageable){
		return noticeRepository.getNoticeLists(noticeSearchDto, pageable);
	}
	
	// 조회수
	@Transactional
	public int updateCnt(Long num) {
		
		return noticeRepository.updateCnt(num);
	}
	
	// 게시판 디테일 불러오기
	@Transactional
	public NoticeDto getnoticeDetail(Long num) {

		List<Attach> attachList = attachRepository.findByNoticeNumOrderByNumAsc(num);
		List<AttachDto> attachDtoList = new ArrayList<>();
		
		
		for (Attach attach : attachList) {

			AttachDto attachDto = AttachDto.of(attach);
			attachDtoList.add(attachDto);
		}
		
		Notice notice = noticeRepository.findByNum(num);
		
		long allNoticeCnt = noticeRepository.getList();
		String prevContent = noticeRepository.getPrevContent(num);
		String nextContent = noticeRepository.getNextContent(num);
		NoticeDto noticeDto = NoticeDto.of(notice);
		noticeDto.setAllNoticeCnt(allNoticeCnt);
		noticeDto.setPrevContent(prevContent);
		noticeDto.setNextContent(nextContent);
		noticeDto.setAttachDtoList(attachDtoList);
		noticeDto.setMember(null);
		
		return noticeDto;
	}
	
	// 게시판 글 수정하기
	public Long updatenotice(NoticeDto noticeDto, List<MultipartFile> attachList) throws Exception {

		Notice notice = noticeRepository.findById(noticeDto.getNum())
				.orElseThrow(EntityNotFoundException::new);
		
		System.out.println(notice);
		
		notice.updateNotice(noticeDto);
		notice.setRegTime(noticeDto.getRegTime());
		List<Long> attachNums = noticeDto.getAttachNums();
		
		for (int i = 0; i < attachList.size(); i++) {

			attachService.updateAttach(attachNums.get(i), attachList.get(i));
		}
		
		return notice.getNum();
	}
	
	// 게시판 글 삭제하기
	public void deletenotice(Long num)throws Exception{
		
		noticeRepository.deleteByNum(num);
		
	}
	
	public Notice findByNum(Long num) {
		return noticeRepository.findByNum(num);
	}
	
}
