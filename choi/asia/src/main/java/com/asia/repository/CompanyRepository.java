package com.asia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asia.entity.Company;
import com.asia.entity.Member;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	
	Company findById(String id);
	Company findByTel(String tel);
	Company findByEmail(String email);
	
	boolean existsById(String id);
}
