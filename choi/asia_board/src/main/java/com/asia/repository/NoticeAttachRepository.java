package com.asia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asia.entity.Attach;

public interface BoardAttachRepository extends JpaRepository<Attach,Long> {
	
	
}
