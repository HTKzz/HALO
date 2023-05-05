package com.asia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asia.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	Member findByMemid(String Memid);
	boolean existsByMemid(String memid);
	
}
