package com.asia.repository;

import java.time.LocalDateTime;
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
				.where(regDtsAfter(reservationSearchDto.getSearchDateType()),
						searchReservationStatusEq(reservationSearchDto.getStat()),
						searchByLike(reservationSearchDto.getSearchBy(), reservationSearchDto.getSearchQuery()))
				.orderBy(QReservation.reservation.num.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();

		List<Reservation> content = results.getResults();
		long total = results.getTotal();

		return new PageImpl<>(content, pageable, total);
	}
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {

		if (StringUtils.equals("stat", searchBy)) {
			return QReservation.reservation.stat.like("%" + searchQuery + "%");
		} else if (StringUtils.equals("createdBy", searchBy)) {
			return QReservation.reservation.createdBy.like("%" + searchQuery + "%");
		}
		return null;
	}
	
	private BooleanExpression regDtsAfter(String searchDateType) {
		LocalDateTime dateTime = LocalDateTime.now();
		if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
			return null;
		} else if (StringUtils.equals("1d", searchDateType)) {
			dateTime = dateTime.minusDays(1);
		} else if (StringUtils.equals("1w", searchDateType)) {
			dateTime = dateTime.minusWeeks(1);
		} else if (StringUtils.equals("1m", searchDateType)) {
			dateTime = dateTime.minusMonths(1);
		} else if (StringUtils.equals("6m", searchDateType)) {
			dateTime = dateTime.minusMonths(6);
		}
		return QReservation.reservation.regTime.after(dateTime);
	}
	
	private BooleanExpression searchReservationStatusEq(String reservationStat) {
		return reservationStat == null ? null : QReservation.reservation.stat.eq(reservationStat);
	}
	
}

