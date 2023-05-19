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
	
//	@Query(value="select LEVEL, group_ord, group_ord, origin_no, from Board START WITH origin_no=0 connect by prior board_id=parentNO"
//			+ " order siblings by board_id DESC;", nativeQuery=true)
//	Long getGroupOrd(Long groupOrd);
	
//	LPAD(' ', 4*(LEVEL-1)) || name name
}
