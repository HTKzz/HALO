package com.asia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asia.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
	
	Board findByNum(Long num);
	Board findByName(String name);
	Board findByContent(String content);

	void deleteByNum(Long num);
	
	boolean existsByName(String name);
	
}
