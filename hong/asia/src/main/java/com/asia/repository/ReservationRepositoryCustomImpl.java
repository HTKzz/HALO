package com.asia.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.asia.dto.ReservationSearchDto;
import com.asia.entity.QReservation;
import com.asia.entity.Reservation;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class ReservationRepositoryCustomImpl implements ReservationRepositoryCustom {
	
	private JPAQueryFactory queryFactory;

	public ReservationRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	public Page<Reservation> getAdminItemPage(ReservationSearchDto reservationSearchDto, Pageable pageable) {

		QueryResults<Reservation> results = queryFactory.selectFrom(QReservation.reservation)
				.where(searchByLike(reservationSearchDto.getSearchBy(), reservationSearchDto.getSearchQuery()))
				.orderBy(QReservation.reservation.num.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();

		List<Reservation> content = results.getResults();
		long total = results.getTotal();

		return new PageImpl<>(content, pageable, total);
	}
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {

		if (StringUtils.equals("name", searchBy)) {
			return QReservation.reservation.application.name.like("%" + searchQuery + "%");
		} else if (StringUtils.equals("buyer", searchBy)) {
			return QReservation.reservation.member.id.like("%" + searchQuery + "%");
		} else if (StringUtils.equals("stat", searchBy)) {
			return QReservation.reservation.stat.like("%" + searchQuery + "%");
		} 
		return null;
	}
	
}

