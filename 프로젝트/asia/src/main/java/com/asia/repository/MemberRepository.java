package com.asia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.asia.constant.Stat;
import com.asia.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
	
	Member findById(String id);
	Member findByTel(String tel);
	Member findByEmail(String email);
	Member findByName(String name);
	Member findByNameAndEmail(String name, String email);
	Member findByIdAndEmail(String mid, String email);
	
	boolean existsById(String id);
	
	@Modifying
	@Query(value="update member set password = :password where id = :id", nativeQuery=true)
	void updatememberMyPage(String password, String id);
}
