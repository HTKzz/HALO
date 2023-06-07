package com.asia.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.asia.dto.SearchDto;
import com.asia.entity.QVoc;
import com.asia.entity.Voc;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

//사용자 정의 인터페이스 구현
public class VocRepositoryCustomImpl implements VocRepositoryCustom{
	
	private JPAQueryFactory queryFactory; //동적으로 쿼리를 생성하기 위해 JPAQueryFactory 클래스 사용
	
	public VocRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em); //JPAQueryFactory 생성자로 EntityManager 객체 넣어줌
	}

	
	@Override
	public Page<Voc> getVocLists(SearchDto searchDto, Pageable pageable){
		QueryResults<Voc> results = queryFactory // queryFactory 이용해서 쿼리 생성
				.selectFrom(QVoc.voc) //글 데이터 조회하기 위해 QVoc의 voc 지정
				.where(searchByLike(searchDto.getSearchBy(), searchDto.getSearchQuery()))
				.orderBy(QVoc.voc.originNo.desc(), QVoc.voc.groupOrd.asc()) //우리는 num
				.offset(pageable.getOffset()) // 데이터를 가져 올 시작 인덱스를 지정
				.limit(pageable.getPageSize()) // 한번에 가져 올 최대 개수 지정
				.fetchResults(); // 조회한 리스트 및 전체 개수를 포함하는 QueryResult를 반환, 글 데이터 리스트 조회 및 글 데이터 전체 개수를 조회하는 2번의 쿼리문 실행
		
		List<Voc> contents = results.getResults();
		long total = results.getTotal();
		
		return new PageImpl<>(contents, pageable, total);//조회한 데이터를 Page 클래스의 구현체인 PageImpl로 반환
	
	}

	private BooleanExpression searchByLike(String searchBy, String searchQuery) {

		if (StringUtils.equals("name", searchBy)) {
			return QVoc.voc.name.like("%" + searchQuery + "%");
		} else if (StringUtils.equals("writer", searchBy)) {
			return QVoc.voc.member.id.like("%" + searchQuery + "%");
		}
		return null;
	}
	
}
