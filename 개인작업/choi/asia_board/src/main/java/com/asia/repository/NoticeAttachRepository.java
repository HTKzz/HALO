package com.asia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asia.entity.Attach;

public interface NoticeAttachRepository extends JpaRepository<Attach,Long> {
	
	
}
