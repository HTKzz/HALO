package com.asia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asia.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
	
	Board findByName(String name);
	Board findByContent(String content);
	
//	boolean existsByName(String name);
	
}
