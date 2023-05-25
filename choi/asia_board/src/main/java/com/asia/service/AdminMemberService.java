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
	
	public Page<Member> memberList(Pageable pageable){
		
		return memberRepository.findAll(pageable);
	}
	
}
