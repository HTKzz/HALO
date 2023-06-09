package com.asia.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.asia.entity.Voc;

public interface VocRepository extends JpaRepository<Voc, Long>, QuerydslPredicateExecutor<Voc>, VocRepositoryCustom {

	// 조회수
	@Modifying
	@Query("update Voc v set v.cnt = v.cnt + 1 where v.realNum = :num")
	int updateCnt(Long num);

	// 페이징(vocMng) , VocRepositoryCustom 상속받아서 Querydsl로 구현한 관리페이지목록을 불러오는
	// getBoardPage()메서드 사용가능
	List<Voc> findByName(String name);

	// 삭제
	void deleteByRealNum(Long num);

	// 답글에
	Voc findByNum(Long num);

	// 답글
	@Modifying
	@Query("update Voc set group_Ord = group_Ord + 1 where origin_No = :originNo and group_Ord > :groupOrd")
	void updateGroupOrd(Long originNo, Long groupOrd);
	
	// 상세보기 이전글 다음글
	@Query(value = "select count(*) from Voc", nativeQuery = true)
	long getList();

	@Query(value = "select name from Voc where real_num = :num -1", nativeQuery = true)
	String getPrevContent(Long num);

	@Query(value = "select name from Voc where real_num = :num +1", nativeQuery = true)
	String getNextContent(Long num);
	
	@Query(value = "select real_num from (SELECT * FROM voc ORDER BY real_num DESC) WHERE ROWNUM= 1", nativeQuery = true)
	Long getRealNum();
	
	@Modifying
	@Query("update Voc set real_num = real_num + 1 where real_Num >= :realNum")
	void updateRealNum(Long realNum);
	
	@Modifying
	@Query("update Voc set real_num = real_num - 1 where real_Num > :realNum")
	void DeleteRealNum(Long realNum);

	Voc findByRealNum(Long num);
}
