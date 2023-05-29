package com.asia.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.asia.dto.MainApplicationDto;
import com.asia.dto.QMainApplicationDto;
import com.asia.entity.QApplication;
import com.asia.entity.QAttach;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;

// 인터페이스를 상속하여 구현한다
public class ApplicationRepositoryCustomImpl implements ApplicationRepositoryCustom {
	
	private JPAQueryFactory queryFactory;
	// 동적으로 쿼리를 생성하기 위해서 JPAQueryFactory 클래스를 사용한다.
//	
	public ApplicationRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
		// JPAQueryFactory 생성자로 EntityManager 객체를 넣어준다.
	}
//	
//	private BooleanExpression searchProgramCategoryEq(ProgramCategory searchProgramCategory){
//		return searchProgramCategory == null ? null : QApplication.application.programCategory.eq(searchProgramCategory);
//	}   // 상품 판매 상태조건이 전체(null)일 경우는 null을 리턴, 결과값이 null이면 where절에서 해당조건은 무시. 상품판매 상태조건이 null이 아니라
//		// 판매중 혹은 품절 상태라면 해당조건 상품만 조회한다.
	
//	private BooleanExpression regDtsAfter(String searchDateType){   // searchDateType의 값에 따라서 dateTime의 값을 이전 시간의 값으로
//		                                                            // 세팅 후 해당 시간 이후로 등록된 상품만 조회한다. 예를 들어, searchDateType의 값이 1m인 경우 dateTime의 시간을 한 달 전으로
//		                                                            // 세팅 후 최근 한 달 동안 등록된 상품만 조회하도록 조건값을 반환한다.
//		LocalDateTime dateTime = LocalDateTime.now();
//			
//		if(StringUtils.equals("all", searchDateType) || searchDateType == null) {
//			return null;
//		} else if(StringUtils.equals("1d", searchDateType)) {
//			dateTime = dateTime.minusDays(1);
//		} else if(StringUtils.equals("1w", searchDateType)){
//			dateTime = dateTime.minusWeeks(1);
//		} else if(StringUtils.equals("1m", searchDateType)){
//			dateTime = dateTime.minusMonths(1);
//		} else if(StringUtils.equals("6m", searchDateType)){
//			dateTime = dateTime.minusMonths(6);
//		}
//		return QApplication.application.regTime.after(dateTime);
//	}
//		
//	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
//		// searchBy의 값에 따라서 상품명에 검색어를 포함하고 있는 상품 또는 상품 생성자의 아이디에 검색어를 포함하고 있는 상품을 조회하도록 조건값을 반환한다.
//			
//		if(StringUtils.equals("name", searchBy)) {
//			return QApplication.application.name.like ("%" + searchQuery + "%");
//		} else if(StringUtils.equals("createdBy", searchBy)) {
//			return QApplication.application.createdBy.like("%" + searchQuery + "%");
//		}
//		return null;
//	}
		
//	@Override
//	public Page<Application> getApplicationPage(ApplicationSearchDto applicationSearchDto, Pageable pageable) {
//			
//		QueryResults<Application> results = queryFactory   // queryFactory를 이용해서 쿼리를 생성한다.
//					.selectFrom(QApplication.application)   // 상품 데이터를 조회하기 위해서 QItem의 item을 지정한다.
//					.where(regDtsAfter(applicationSearchDto.getSearchDateType()),   // BooleanExpression 반환하는 조건문들을 넣어준다.
//							searchProgramCategoryEq(applicationSearchDto.getSearchProgramCategory()),   // 컴마(,)단위로 넣어줄 경우 and조건으로 인식한다.
//							searchByLike(applicationSearchDto.getSearchBy(),
//									applicationSearchDto.getSearchQuery()))
//					.orderBy(QApplication.application.num.desc())
//					.offset(pageable.getOffset())   // 데이터를 가지고 올 시작 인덱스를 지정한다.
//					.limit(pageable.getPageSize())   // 한번에 가지고 올 최대 개수를 지정한다.
//					.fetchResults();   // 조회한 리스트 및 전체 개수를 포함하는 QueryResult를 반환한다. 상품 데이터 리스트 조회 및 상품 데이터 전체 개수를
//									// 조회하는 2번의 쿼리문이 실행된다.
//			
//		List<Application> content = results.getResults();
//	long total = results.getTotal();
//		
//	return new PageImpl<>(content, pageable, total);   // 조회한 데이터를 Page 클래스의 구현체인 PageImpl 객체로 반환한다.
//	}
//	
//	private BooleanExpression nameLike(String searchQuery){   // 검색어가 null이 아니면 상품명에 해당 검색어가 포함되는 상품을 조회하는 조건을 반환한다.
//		return StringUtils.isEmpty(searchQuery) ? null : QApplication.application.name.like("%" + searchQuery + "%");
//	}
	
//	@Override
//	public Page<ApplicationDto> getMainApplicationPage(ApplicationSearchDto applicationSearchDto, Pageable pageable) {
//		QApplication application = QApplication.application;
//		QAttach attach = QAttach.attach;
//		
//		QueryResults<MainApplicationDto> results = queryFactory
//				.select(
//						new QMainApplicationDto(   // QMainItemDto의 생성자에 반환할 값들을 넣어준다. @QueryProjection 을 사용하면 DTO로 바로 조회가 가능하다.
//								application.num,   // 엔티티 조회 후 DTO로 변환하는 과정을 줄일 수 있다.
//								application.name,
//								application.detail,
//								attach.url,
//								application.price)
//		)
//		.from(attach)
//		.join(attach.application, application)   // itemImg와 item을 내부 조인한다.
//		.where(attach.repimgYn.eq("Y"))   // 상품 이미지의 경우 대표 상품 이미지만 불러온다.
//		.where(nameLike(applicationSearchDto.getSearchQuery()))
//		.orderBy(application.num.desc())
//		.offset(pageable.getOffset())
//		.limit(pageable.getPageSize())
//		.fetchResults();
//		
//	List<MainApplicationDto> content = results.getResults();
//	long total = results.getTotal();
//	return new PageImpl<>(content, pageable, total);
//
//	}
	
	@Override
	public Page<MainApplicationDto> findDistinctByProgramCategory(Pageable pageable, String programCategory) {
		QApplication application = QApplication.application;
		QAttach attach = QAttach.attach;
		
		QueryResults<MainApplicationDto> results = queryFactory
				.select(
						new QMainApplicationDto(   // QMainItemDto의 생성자에 반환할 값들을 넣어준다. @QueryProjection 을 사용하면 DTO로 바로 조회가 가능하다.
								application.num,   // 엔티티 조회 후 DTO로 변환하는 과정을 줄일 수 있다.
								application.name,
								application.detail,
								attach.url,
								application.price,
								application.sdate,
								application.edate)
		)
		.from(attach)
		.join(attach.application, application)   // itemImg와 item을 내부 조인한다.
		.where(attach.thumb.eq("Y"),
				application.programCategory.eq(programCategory))   // 상품 이미지의 경우 대표 상품 이미지만 불러온다.
//		.where(nameLike(applicationSearchDto.getSearchQuery()))
		.orderBy(application.num.desc())
		.offset(pageable.getOffset())
		.limit(pageable.getPageSize())
		.fetchResults();
		
	List<MainApplicationDto> content = results.getResults();
	long total = results.getTotal();
	return new PageImpl<>(content, pageable, total);

	}
	

}
