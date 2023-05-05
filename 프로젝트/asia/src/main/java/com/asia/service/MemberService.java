package com.asia.service;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.asia.entity.Member;
import com.asia.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
	private final MemberRepository memberRepository;

	public Member saveMember(Member member) {
		validateDuplicateMember(member);
		return memberRepository.save(member);
	}

	private void validateDuplicateMember(Member member) {
		Member findMember = memberRepository.findByMemid(member.getMemid());
		if (findMember != null) {
			throw new IllegalStateException("이미 가입된 회원입니다."); // 이미 가입된 회원의 경우 예외를 발생시킨다.
		}
	}

	public UserDetails loadUserByUsername(String memid) throws UsernameNotFoundException {
		
		Member member = memberRepository.findByMemid(memid);
		
		if (member == null) {
			throw new UsernameNotFoundException(memid);
		}
		
		return User.builder()
				.username(member.getMemid())
				.password(member.getPassword())
				.roles(member.getRole().toString())
				.build();
	}
	
	public boolean checkMemidDuplicate(String memid) {
		return memberRepository.existsByMemid(memid);
	}
}
