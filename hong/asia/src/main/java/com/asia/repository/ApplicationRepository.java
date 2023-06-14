package com.asia.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.asia.dto.ApplicationDto;
import com.asia.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long>, QuerydslPredicateExecutor<Application>, ApplicationRepositoryCustom {
	
	void deleteByNum(Long num);
	
	List<Application> findByOriginNo(Long num);
	
	ApplicationDto findByNum(Long num);
	
	@Query(value="select * from Application where app_num = :num", nativeQuery = true)
	Application getApplication(Long num);
	
	@Query("select distinct new com.asia.dto.ApplicationDto(num, name, sdate, edate, udate) from Application "
			+ "where origin_no = :num")
	List<ApplicationDto> getList2(Long num);
	
	@Query(value="select * from Application where app_num IN (:nums) order by sdate asc", nativeQuery = true)
	List<Application> findByNums(@Param("nums") List<Long> nums);
	
	@Modifying
	@Query(value = "update APPLICATION a set a.APPROVAL_STATUS = '승인' where a.app_num = :num", nativeQuery = true)
	void updateApprovalStatus(Long num);
	
	@Query(value = "SELECT TB.app_num FROM(SELECT ROW_NUMBER() OVER(PARTITION BY application.origin_no ORDER BY application.udate ASC ) AS RNUM, application.* FROM application ) TB WHERE RNUM = 1 and APPROVAL_STATUS = '승인'", nativeQuery = true)
	List<Long> getAppNumNoPC();
	
	@Query(value = "SELECT TB.app_num FROM(SELECT ROW_NUMBER() OVER(PARTITION BY application.origin_no ORDER BY application.udate ASC ) AS RNUM, application.* FROM application ) TB WHERE RNUM = 1 and program_Category = :programCategory and APPROVAL_STATUS = '승인'", nativeQuery = true)
	List<Long> getAppNum(String programCategory);
}
