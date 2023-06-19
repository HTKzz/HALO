package com.asia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.asia.dto.NoticeDto;
import com.asia.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeRepositoryCustom {
	
	Notice findByNum(Long num);

	void deleteByNum(Long num);
	
	@Modifying
	@Query("update Notice n set n.cnt = n.cnt + 1 where n.num = :num")
	int updateCnt(Long num);
	
	@Query(value="select count(*) from Notice", nativeQuery=true)
	long getList();
	
	@Query(value="select name from Notice where Notice_num = :num -1", nativeQuery=true)
	String getPrevContent(Long num);
	
	@Query(value="select name from Notice where Notice_num = :num +1", nativeQuery=true)
	String getNextContent(Long num);
	
	@Query("select new com.asia.dto.NoticeDto(num, name, d_date) from Notice order by num desc")
	List<NoticeDto> getNoticeList();
}
