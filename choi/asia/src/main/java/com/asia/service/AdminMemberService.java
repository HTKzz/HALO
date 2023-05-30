package com.asia.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.asia.entity.Member;
import com.asia.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminMemberService {

	private final MemberRepository memberRepository;

	// 회원관리 페이지 리스트 불러오기
	public Page<Member> memberList(Pageable pageable) {

		return memberRepository.findAll(pageable); // 모든 리스트
	}

	// 회원관리 페이지 검색
	public Page<Member> searchMemberByName(String memberMngSearch, Pageable pageable) {
		return memberRepository.findByNameContaining(memberMngSearch, pageable); // 이름으로 찾아오기
	}

	public Page<Member> searchMemberByTel(String memberMngSearch, Pageable pageable) {
		return memberRepository.findByTelContaining(memberMngSearch, pageable); // 전화번호로 찾아오기
	}

	public Page<Member> searchMemberByEmail(String memberMngSearch, Pageable pageable) {
		return memberRepository.findByEmailContaining(memberMngSearch, pageable); // 이메일로 찾아오기
	}

	public Page<Member> searchMemberByBirth(String memberMngSearch, Pageable pageable) {
		return memberRepository.findByBirthContaining(memberMngSearch, pageable); // 생년으로 찾아오기
	}

	public Page<Member> searchMemberByJoin(String memberMngSearch, Pageable pageable) {
		return memberRepository.findByJoinContaining(memberMngSearch, pageable); // 가입일로 찾아오기
	}
	
	public Page<Member> searchMemberByStat(String memberMngSearch, Pageable pageable) {
		return memberRepository.findByStatContaining(memberMngSearch, pageable); // 상태로 찾아오기
	}

	public Page<Member> searchMemberByRole(String memberMngSearch, Pageable pageable) {
		
		String memberMngSearch1 = memberMngSearch.toUpperCase();
		return memberRepository.findByRoleContaining(memberMngSearch1, pageable); // 권한으로 찾아오기
	}
}
