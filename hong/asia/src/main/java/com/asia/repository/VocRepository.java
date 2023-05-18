package com.asia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.asia.entity.Voc;

public interface VocRepository extends JpaRepository<Voc, Long> ,QuerydslPredicateExecutor<Voc>, VocRepositoryCustom{
	
	//조회수
	@Modifying
	@Query("update Voc v set v.cnt = v.cnt + 1 where v.num = :num")
	int updateCnt(Long num);
	
	//페이징(vocMng) , VocRepositoryCustom 상속받아서 Querydsl로 구현한 관리페이지목록을 불러오는 getBoardPage()메서드 사용가능
	List<Voc> findByName(String name);
	
	Voc findByNum(Long num);
	
	//삭제
	void deleteByNum(Long num);
	
//	@Query("SELECT level, voc_num, parent_NO, name, content, "
//			+ "reg_time from Voc START WITH parent_NO=0 CONNECT BY PRIOR "
//			+ "voc_num=parent_NO ORDER SIBLINGS BY voc_num DESC")
//	List selectAllVocList();
	
	@Modifying
	@Query("update Voc set group_Ord = group_Ord + 1 where origin_No = :originNo and group_Ord > :groupOrd")
	void updateGroupOrd(Long originNo, Long groupOrd);
}
