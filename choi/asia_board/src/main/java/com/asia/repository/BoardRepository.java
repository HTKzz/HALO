package com.asia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.asia.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
	
	Board findByNum(Long num);
	Board findByName(String name);
	Board findByContent(String content);

	void deleteByNum(Long num);
	
	boolean existsByName(String name);
	
	@Modifying
	@Query("update Board b set b.cnt = b.cnt + 1 where b.num = :num")
	int updateCnt(Long num);
	
}
