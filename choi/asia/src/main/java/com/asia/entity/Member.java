package com.asia.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.asia.constant.Role;
import com.asia.constant.Stat;
import com.asia.dto.CompanyFormDto;
import com.asia.dto.MemberFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
@EntityListeners(value = { AuditingEntityListener.class })
@SequenceGenerator(name = "MEMBER_SEQ_GEN", // 시퀀스 제너레이터 이름
		sequenceName = "MEMBER_SEQ", // 시퀀스 이름
		initialValue = 1, // 시작값
		allocationSize = 1 // 메모리를 통해 할당할 범위 사이즈
)
public class Member {

	@Id
	@Column(name = "num")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GEN")
	private Long num;

	@Column(unique = true, nullable = false)
	private String id;

	@Column(unique = true)
	private String cid;

	@Column(nullable = false)
	private String name;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(unique = true, nullable = false)
	private String tel;

	private String birth;

	private String addr;

	@Column(nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	private Stat stat;

	@Enumerated(EnumType.STRING)
	private Role role;

	private Long age;

	private String agree;

	private String join;

	// 회원가입
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		Member member = new Member();
		member.setId(memberFormDto.getId());
		member.setName(memberFormDto.getName());
		member.setEmail(memberFormDto.getEmail());
		member.setTel(memberFormDto.getTel());
		member.setBirth(memberFormDto.getBirth());
		member.setAddr(memberFormDto.getAddr());
		member.setAge(memberFormDto.getAge());
		member.setAgree(memberFormDto.getAgree());
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		member.setPassword(password);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy년 MM월 dd일 HH시 mm분");
		String join1 = LocalDateTime.now().format(formatter); // LocalDateTime -> String 타입 변환하기
		member.setJoin(join1);
		member.setRole(Role.USER);
		member.setStat(Stat.회원);
		return member;
	}

	public static Member createCompany(CompanyFormDto companyFormDto, PasswordEncoder passwordEncoder) {
		Member member = new Member();
		member.setId(companyFormDto.getId());
		member.setCid(companyFormDto.getCid());
		member.setName(companyFormDto.getName());
		member.setEmail(companyFormDto.getEmail());
		member.setTel(companyFormDto.getTel());
		member.setAddr(companyFormDto.getAddr());
		member.setAgree(companyFormDto.getAgree());
		String password = passwordEncoder.encode(companyFormDto.getPassword());
		member.setPassword(password);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy년 MM월 dd일 HH시 mm분");
		String join1 = LocalDateTime.now().format(formatter); // LocalDateTime -> String 타입 변환하기
		member.setJoin(join1);
		member.setRole(Role.COMPANY);
		member.setStat(Stat.회원);
		return member;
	}
}
