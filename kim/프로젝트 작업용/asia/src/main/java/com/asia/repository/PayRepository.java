package com.asia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asia.entity.Pay;

public interface PayRepository extends JpaRepository<Pay, Long> {
	

}
