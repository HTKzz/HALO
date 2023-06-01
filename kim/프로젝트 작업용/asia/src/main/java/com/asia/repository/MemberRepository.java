package com.asia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.asia.constant.Stat;
import com.asia.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	Member findById(String id);
	Member findByTel(String tel);
	Member findByEmail(String email);
	Member findByName(String name);
	Member findByNameAndEmail(String name, String email);
	Member findByIdAndEmail(String mid, String email);
	
	boolean existsById(String id);
	Page<Member> findByNameContaining(String memberMngSearch, Pageable pageable);
	Page<Member> findByTelContaining(String memberMngSearch, Pageable pageable);
	Page<Member> findByEmailContaining(String memberMngSearch, Pageable pageable);
	Page<Member> findByBirthContaining(String memberMngSearch, Pageable pageable);
	Page<Member> findByJoinContaining(String memberMngSearch, Pageable pageable);
	Page<Member> findByStatContaining(Stat stat, Pageable pageable);
	Page<Member> findByRoleContaining(String memberMngSearch, Pageable pageable);
	
}
