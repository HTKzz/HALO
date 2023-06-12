package com.asia.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.asia.constant.Role;
import com.asia.constant.Stat;
import com.asia.dto.SearchDto;
import com.asia.entity.Member;
import com.asia.entity.QMember;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{
	
	private JPAQueryFactory queryFactory;
	
	public MemberRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em); //JPAQueryFactory 생성자로 EntityManager 객체 넣어줌
	}
	
	@Override
	public Page<Member> getMemberMngLists(SearchDto searchDto, Pageable pageable){
		QueryResults<Member> results = queryFactory
				.selectFrom(QMember.member)
				.where(searchOptionLike(searchDto.getSearchBy(), searchDto.getSearchQuery()))
				.orderBy(QMember.member.join.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();
		
		List<Member> contents = results.getResults();
		long total = results.getTotal();
		
		return new PageImpl<>(contents, pageable, total);
	}
	
	private BooleanExpression searchOptionLike(String searchBy, String searchQuery) {

		if (StringUtils.equals("name", searchBy)) {
			return QMember.member.name.like("%" + searchQuery + "%");
		} else if (StringUtils.equals("id", searchBy)) {
			return QMember.member.id.like("%" + searchQuery + "%");
		} else if (StringUtils.equals("tel", searchBy)) {
			return QMember.member.tel.like("%" + searchQuery + "%");
		} else if (StringUtils.equals("email", searchBy)) {
			return QMember.member.email.like("%" + searchQuery + "%");
		} else if (StringUtils.equals("birth", searchBy)) {
			return QMember.member.birth.like("%" + searchQuery + "%");
		} else if (StringUtils.equals("join", searchBy)) {
			return QMember.member.join.like("%" + searchQuery + "%");
		} else if (StringUtils.equals("stat", searchBy) && Stat.회원.toString().contains(searchQuery)) {
			return searchQuery == "" ? null : QMember.member.stat.eq(Stat.회원);
		} else if (StringUtils.equals("stat", searchBy) && Stat.블랙.toString().contains(searchQuery)) {
			return searchQuery == "" ? null : QMember.member.stat.eq(Stat.블랙);
		} else if (StringUtils.equals("role", searchBy) && Role.ADMIN.toString().contains(searchQuery.toUpperCase())) {
			return searchQuery == "" ? null : QMember.member.role.eq(Role.ADMIN);
		} else if (StringUtils.equals("role", searchBy) && Role.USER.toString().contains(searchQuery.toUpperCase())) {
			return searchQuery == "" ? null : QMember.member.role.eq(Role.USER);
		} else if (StringUtils.equals("role", searchBy) && Role.COMPANY.toString().contains(searchQuery.toUpperCase())) {
			return searchQuery == "" ? null : QMember.member.role.eq(Role.COMPANY);
		} else if ((StringUtils.equals("role", searchBy) || StringUtils.equals("stat", searchBy)) && searchQuery != "") {
			return QMember.member.join.like(""); // 의미 없음
		}
		return null;
	}
	
}
