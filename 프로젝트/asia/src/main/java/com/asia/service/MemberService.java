package com.asia.service;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.asia.constant.Stat;
import com.asia.dto.SearchDto;
import com.asia.entity.Member;
import com.asia.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;

	// 회원가입
	public Member saveMember(Member member) {
		validateDuplicateMember(member);
		return memberRepository.save(member);
	}

	// 회원 중복검사
	private void validateDuplicateMember(Member member) {
		Member findMember = memberRepository.findById(member.getId());
		if (findMember != null) {
			throw new IllegalStateException("이미 가입된 아이디입니다."); // 이미 가입된 회원의 경우 예외를 발생시킨다.
		}
		findMember = null;
		findMember = memberRepository.findByEmail(member.getEmail());
		if (findMember != null) {
			throw new IllegalStateException("이미 사용된 이메일입니다.");
		}
		findMember = null;
		findMember = memberRepository.findByTel(member.getTel());
		if (findMember != null) {
			throw new IllegalStateException("이미 사용된 전화번호입니다.");
		}
		findMember = null;
		findMember = memberRepository.findByCid(member.getCid());
		if (findMember != null) {
			throw new IllegalStateException("이미 사용된 사업자등록번호입니다.");
		}
	}

	// 로그인
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		Member member = memberRepository.findById(id);
		if (member == null) {
			throw new UsernameNotFoundException(id);
		}

		return User.builder().username(member.getId()).password(member.getPassword()).roles(member.getRole().toString())
				.build();
	}

	public boolean checkIdDuplicate(String id) {
		return memberRepository.existsById(id);
	}
	
	// 아이디 찾기
	public Member findByNameAndEmail(String name, String email) {
		Member member = memberRepository.findByNameAndEmail(name, email);
		if (member != null) {
			return member;
		} else {
			throw new NullPointerException("가입된 회원이 아닙니다");
		}
	}
	
	// 비밀번호 찾기
	public Member findByIdAndEmail(String id, String email) {
		Member member = memberRepository.findByIdAndEmail(id, email);
		if (member != null) {
			return member;
		} else {
			throw new NullPointerException("가입된 회원이 아닙니다");
		}
	}
	
	public Member updateMember(Member member) {
		return memberRepository.save(member);
	}
	
	//마이페이지 멤버정보 불러오기
    public Member findUserMyPage(String name) {
        return memberRepository.findById(name);
    }
    
    // 마이페이지 비밀번호 수정
    public void updateMemberPwd(String password, String id) {
    	memberRepository.updatememberMyPage(password, id);
    }
    
    // 회원관리 페이지 리스트 불러오기
 	public Page<Member> memberList(SearchDto searchDto, Pageable pageable) {
 		return memberRepository.getMemberMngLists(searchDto, pageable); // 모든 리스트
 	}
 	
 	public Member getMemDtl(Long num) {
 		Member member = memberRepository.findByNum(num);
 		return member;
 	}
 	
 	//회원 상태 수정
 	public void updateStat(Member member) {
 		if(member.getStat().toString().equals("회원")) {
 			member.setStat(Stat.블랙);
 	 		memberRepository.save(member);
 		} else {
 			member.setStat(Stat.회원);
 	 		memberRepository.save(member);
 		}
 	}
 	
 	

}
