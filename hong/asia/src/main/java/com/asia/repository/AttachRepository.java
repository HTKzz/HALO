package com.asia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asia.entity.Attach;

public interface AttachRepository extends JpaRepository<Attach, Long> {
	
	List<Attach> findByApplicationNumOrderByNumAsc(Long appnum);
	
	Attach findByApplicationNumAndThumb(Long appnum, String thumb);
	
}  
