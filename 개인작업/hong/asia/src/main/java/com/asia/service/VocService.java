package com.asia.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.asia.dto.AttachDto;
import com.asia.dto.SearchDto;
import com.asia.dto.VocFormDto;
import com.asia.entity.Attach;
import com.asia.entity.Member;
import com.asia.entity.Voc;
import com.asia.repository.AttachRepository;
import com.asia.repository.MemberRepository;
import com.asia.repository.VocRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class VocService {

//	private final Logger LOGGER = LoggerFactory.getLogger(VocService.class);

	private final VocRepository vocRepository;
	private final AttachService attachService;
	private final AttachRepository attachRepository;
	private final MemberRepository memberRepository;

	// 조회수
	@Transactional
	public void updateCnt(Long num) {
		vocRepository.updateCnt(num);
	}

	// 새글등록
	public Long saveVoc(VocFormDto vocFormDto, List<MultipartFile> attachFileList, String name) throws Exception {

		Member member = memberRepository.findById(name);

		Voc voc = vocFormDto.createVoc();
		voc.setGroupOrd((long) 0);
		voc.setGroupLayer((long) 0);

		Long realNum = vocRepository.getRealNum();

		if (realNum == null) {
			voc.setRealNum((long) 1);
		} else {
			voc.setRealNum(realNum + 1);
		}

		voc.setCnt(1);
		voc.setMember(member);
		vocRepository.save(voc);

		voc.setOriginNo(voc.getNum());
		vocRepository.save(voc);

		// 이미지등록
		for (int i = 0; i < attachFileList.size(); i++) {
			Attach attach = new Attach();
			attach.setVoc(voc);

			if (i == 0)// 첫번째 이미지일 경우 대표이미지여부값을 Y로 세팅. 나머지는 N
				attach.setThumb("Y");
			else
				attach.setThumb("N");
			attachService.saveAttach(attach, attachFileList.get(i));
		}
		return voc.getNum();
	}

	// 삭제
	public void vocDelete(Long num) {
		vocRepository.deleteByRealNum(num);
		vocRepository.DeleteRealNum(num);
	}

	// 수정-등록된 상품 불러오는 메서드
	public VocFormDto getVocDtl(Long num) {
		Voc voc = vocRepository.findByRealNum(num);

		List<Attach> attachList = attachRepository.findByVocNumOrderByNumAsc(voc.getNum()); // 해당 이미지 조회
		List<AttachDto> attachDtoList = new ArrayList<AttachDto>();
		for (Attach attach : attachList) { // 조회한 attach엔티티를 attachDto객체로 만들어서 리스트에 추가
			AttachDto attachDto = AttachDto.of(attach);
			attachDtoList.add(attachDto);
		}

		long allVocCnt = vocRepository.getList();

		String prevContent = vocRepository.getPrevContent(voc.getRealNum());
		if (prevContent != null) {
			String[] array = prevContent.split(",");
			if (Integer.parseInt(array[1]) > 0) {
				prevContent = "[답변] " + array[0];
			} else {
				prevContent = array[0];
			}
		}

		String nextContent = vocRepository.getNextContent(voc.getRealNum());
		if (nextContent != null) {
			String[] array = nextContent.split(",");
			if (Integer.parseInt(array[1]) > 0) {
				nextContent = "[답변] " + array[0];
			} else {
				nextContent = array[0];
			}
		}

		VocFormDto vocFormDto = VocFormDto.of(voc);
		vocFormDto.setAllVocCnt(allVocCnt);
		vocFormDto.setPrevContent(prevContent);
		vocFormDto.setNextContent(nextContent);
		vocFormDto.setAttachDtoList(attachDtoList);

		return vocFormDto;
	}

	// 수정-등록된 상품 불러오는 메서드
	public VocFormDto getVocModifyDtl(Long num) {
		Voc voc = vocRepository.findByRealNum(num);

		List<Attach> attachList = attachRepository.findByVocNumOrderByNumAsc(voc.getNum()); // 해당 이미지 조회
		List<AttachDto> attachDtoList = new ArrayList<AttachDto>();
		for (Attach attach : attachList) { // 조회한 attach엔티티를 attachDto객체로 만들어서 리스트에 추가
			AttachDto attachDto = AttachDto.of(attach);
			attachDtoList.add(attachDto);
		}

		VocFormDto vocFormDto = VocFormDto.of(voc);
		vocFormDto.setAttachDtoList(attachDtoList);

		return vocFormDto;
	}

	public Long updateVoc(VocFormDto vocFormDto, List<MultipartFile> attachFileList) throws Exception {
		// 글 수정
		Voc voc = vocRepository.findById(vocFormDto.getNum()).orElseThrow(EntityNotFoundException::new);
		voc.updateVoc(vocFormDto); // 수정화면으로 전달 받은 noticeFormDto를 통해 Notice엔티티 업데이트

		// 이미지 수정
		List<Long> attachIds = vocFormDto.getAttachIds(); // 이미지 아이디 리스트 반환

		for (int i = 0; i < attachFileList.size(); i++) {
			attachService.updateAttach(attachIds.get(i), attachFileList.get(i));// 이미지아이디를 업데이트하기 위해서 이미지 아이디, 이미지 파일정보
																				// 전달
		}

		return voc.getNum();
	}

	public Page<Voc> getVocLists(SearchDto searchDto, Pageable pageable) {
		return vocRepository.getVocLists(searchDto, pageable);
	}

	// 답글등록
	public Long saveReplyVoc(VocFormDto vocFormDto, List<MultipartFile> attachFileList, Long parentNo, String name)
			throws Exception {

		Member member = memberRepository.findById(name);

		Voc presentVoc = vocRepository.findByRealNum(parentNo);// 답글다는글

		Voc voc = vocFormDto.createVoc();
		String reply = "";

		voc.setName(voc.getName());
		voc.setOriginNo(presentVoc.getOriginNo());
		voc.setGroupOrd(presentVoc.getGroupOrd());
		voc.setGroupLayer(presentVoc.getGroupLayer());
		vocRepository.updateRealNum(presentVoc.getRealNum());
		voc.setRealNum(presentVoc.getRealNum());
		voc.setMember(member);
		vocRepository.save(voc);
		vocRepository.updateGroupOrd(voc.getOriginNo(), presentVoc.getGroupOrd());

		if (presentVoc.getGroupLayer() == voc.getGroupLayer()) {
			voc.setGroupOrd(presentVoc.getGroupOrd() + 1);
			voc.setGroupLayer(presentVoc.getGroupLayer() + 1);
		}

//		for(int i = 0; i < voc.getGroupLayer(); i++) {
//			reply += "Re: ";
//		}

		voc.setName(reply + voc.getName());
		vocRepository.save(voc);

		// 이미지등록
		for (int i = 0; i < attachFileList.size(); i++) {
			Attach attach = new Attach();
			attach.setVoc(voc);

			if (i == 0)// 첫번째 이미지일 경우 대표이미지여부값을 Y로 세팅. 나머지는 N
				attach.setThumb("Y");
			else
				attach.setThumb("N");
			attachService.saveAttach(attach, attachFileList.get(i));
		}
		return null;

	}

	public VocFormDto getVoc() { // 글 등록 후 바로 상세보기로
		Long vocNum = vocRepository.getRealNum();

		Voc voc = vocRepository.findByRealNum(vocNum);

		List<Attach> attachList = attachRepository.findByVocNumOrderByNumAsc(voc.getNum()); // 해당 이미지 조회
		List<AttachDto> attachDtoList = new ArrayList<AttachDto>();
		for (Attach attach : attachList) { // 조회한 attach엔티티를 attachDto객체로 만들어서 리스트에 추가
			AttachDto attachDto = AttachDto.of(attach);
			attachDtoList.add(attachDto);
		}

		VocFormDto vocFormDto = VocFormDto.of(voc);

		long allVocCnt = vocRepository.getList();

		String prevContent = vocRepository.getPrevContent(voc.getRealNum());
		if (prevContent != null) {
			String[] array = prevContent.split(",");
			if (Integer.parseInt(array[1]) > 0) {
				prevContent = "[답변] " + array[0];
			} else {
				prevContent = array[0];
			}
		}

		String nextContent = vocRepository.getNextContent(voc.getRealNum());
		if (nextContent != null) {
			String[] array = nextContent.split(",");
			if (Integer.parseInt(array[1]) > 0) {
				nextContent = "[답변] " + array[0];
			} else {
				nextContent = array[0];
			}
		}

		vocFormDto.setAllVocCnt(allVocCnt);
		vocFormDto.setPrevContent(prevContent);
		vocFormDto.setNextContent(nextContent);
		vocFormDto.setAttachDtoList(attachDtoList);

		return vocFormDto;
	}

	public Voc findByRealNum(Long num) {
		return vocRepository.findByRealNum(num);
	}
}
