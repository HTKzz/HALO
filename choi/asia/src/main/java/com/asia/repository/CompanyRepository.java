package com.asia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asia.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	
	Company findById(String id);
//	
	boolean existsById(String id);
}
