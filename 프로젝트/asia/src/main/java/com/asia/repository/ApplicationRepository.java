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
	
	Page<Application> findAll(Pageable pageable);
	
	List<Application> findByName(String name);
	
	ApplicationDto findByNum(Long num);
	
	@Query(value="select * from Application where num = :num", nativeQuery = true)
	Application getApplication(Long num);
	
	@Query("select distinct new com.asia.dto.ApplicationDto(name, sdate, edate) from Application where program_Category = :programCategory and APPROVAL_STATUS = '승인' order by sdate asc")
	List<ApplicationDto> getList1(String programCategory);
	
	@Query("select distinct new com.asia.dto.ApplicationDto(num, name, sdate, edate, udate) from Application "
			+ "where name = :name")
	List<ApplicationDto> getList2(String name);
	
	@Query(value="select * from Application where num IN (:nums) order by sdate asc", nativeQuery = true)
	List<Application> findByNums(@Param("nums") List<Long> nums);
	
	@Modifying
	@Query(value = "update APPLICATION a set a.APPROVAL_STATUS = '승인' where a.NUM = :num", nativeQuery = true)
	void updateApprovalStatus(Long num);
	
	@Query("select distinct new com.asia.dto.ApplicationDto(num, name, sdate, edate) from Application where APPROVAL_STATUS = '승인' order by sdate asc")
	List<ApplicationDto> getSlideList();
	
	@Query(value = "SELECT TB.num FROM(SELECT ROW_NUMBER() OVER(PARTITION BY application.name ORDER BY application.sdate DESC ) AS RNUM, application.* FROM application ) TB WHERE RNUM = 1 and APPROVAL_STATUS = '승인'", nativeQuery = true)
	List<Long> getAppNumNoPC();
	
	@Query(value = "SELECT TB.num FROM(SELECT ROW_NUMBER() OVER(PARTITION BY application.name ORDER BY application.sdate DESC ) AS RNUM, application.* FROM application ) TB WHERE RNUM = 1 and program_Category = :programCategory and APPROVAL_STATUS = '승인'", nativeQuery = true)
	List<Long> getAppNum(String programCategory);

	List<Application> findByName(long num);
}
