package com.asia.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.asia.dto.ApplicationSearchDto;
import com.asia.entity.Application;
import com.asia.entity.QApplication;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

// 인터페이스를 상속하여 구현한다
public class ApplicationRepositoryCustomImpl implements ApplicationRepositoryCustom {

	private JPAQueryFactory queryFactory;
	// 동적으로 쿼리를 생성하기 위해서 JPAQueryFactory 클래스를 사용한다.

	public ApplicationRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
		// JPAQueryFactory 생성자로 EntityManager 객체를 넣어준다.
	}

	// 상품관리 페이징 + 검색기능
	@Override
	public Page<Application> getApplicationList(ApplicationSearchDto applicationSearchDto, Pageable pageable) {
		QueryResults<Application> results = queryFactory.selectFrom(QApplication.application)
				.where(searchByLike(applicationSearchDto.getSearchBy(), applicationSearchDto.getSearchQuery()))
				.orderBy(QApplication.application.num.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize())
				.fetchResults();

		List<Application> contents = results.getResults();
		long total = results.getTotal();

		return new PageImpl<>(contents, pageable, total);
	}

	private BooleanExpression searchByLike(String searchBy, String searchQuery) {

		if (StringUtils.equals("name", searchBy)) {
			return QApplication.application.name.like("%" + searchQuery + "%");
		} else if (StringUtils.equals("writer", searchBy)) {
			return QApplication.application.member.id.like("%" + searchQuery + "%");
		} else if (StringUtils.equals("programCategory", searchBy)) {
			return QApplication.application.programCategory.like("%" + searchQuery + "%");
		} else if (StringUtils.equals("approvalStatus", searchBy)) {
			return QApplication.application.approvalStatus.like(searchQuery + "%");
		}
		return null;
	}

}
