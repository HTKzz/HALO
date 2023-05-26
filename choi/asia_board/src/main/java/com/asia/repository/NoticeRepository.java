package com.asia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.asia.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
	
	Notice findByNum(Long num);
	Notice findByName(String name);
	Notice findByContent(String content);

	void deleteByNum(Long num);
	
	boolean existsByName(String name);
	
	@Modifying
	@Query("update Notice n set n.cnt = n.cnt + 1 where n.num = :num")
	int updateCnt(Long num);
	
	@Query("select count(*) from Notice n where n.originNo = :origin_no and n.groupLayer = :group_layer")
	long getCount(Long origin_no, Long group_layer);
	
	@Query(value="SELECT * FROM Notice ORDER BY origin_no DESC, group_ord ASC", nativeQuery=true)
	long getSort(Long origin_no, Long group_ord, Long group_layer);
	
	@Query(value="select count(*) from Notice", nativeQuery=true)
	long getList();
	
	@Query(value="select name from Notice where Notice_num = :num -1", nativeQuery=true)
	String getPrevContent(Long num);
	
	@Query(value="select name from Notice where Notice_num = :num +1", nativeQuery=true)
	String getNextContent(Long num);
}
