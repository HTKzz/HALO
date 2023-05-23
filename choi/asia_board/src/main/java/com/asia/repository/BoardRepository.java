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
	
	@Query("select count(*) from Board b where b.originNo = :origin_no and b.groupLayer = :group_layer")
	long getCount(Long origin_no, Long group_layer);
	
	@Query(value="SELECT * FROM board ORDER BY origin_no DESC, group_ord ASC", nativeQuery=true)
	long getSort(Long origin_no, Long group_ord, Long group_layer);
	
	@Query(value="select count(*) from Board", nativeQuery=true)
	long getList();
	
	@Query(value="select name from Board where Board_num = :num -1", nativeQuery=true)
	String getPrevContent(Long num);
	
	@Query(value="select name from Board where Board_num = :num +1", nativeQuery=true)
	String getNextContent(Long num);
}
