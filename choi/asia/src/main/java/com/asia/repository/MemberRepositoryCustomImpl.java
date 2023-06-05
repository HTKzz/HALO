package com.asia.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.asia.constant.Role;
import com.asia.constant.Stat;
import com.asia.dto.MemberFormDto;
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
	public Page<Member> getMemberMngLists(MemberFormDto memberFormDto, Pageable pageable){
		QueryResults<Member> results = queryFactory
				.selectFrom(QMember.member)
				.where(searchOptionLike(memberFormDto.getSearchOption(), memberFormDto.getMemberMngSearch()))
				.orderBy(QMember.member.join.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();
		
		List<Member> contents = results.getResults();
		long total = results.getTotal();
		
		return new PageImpl<>(contents, pageable, total);
	}
	
	private BooleanExpression searchOptionLike(String searchOption, String memberMngSearch) {

		if (StringUtils.equals("name", searchOption)) {
			return QMember.member.name.like("%" + memberMngSearch + "%");
		} else if (StringUtils.equals("tel", searchOption)) {
			return QMember.member.tel.like("%" + memberMngSearch + "%");
		}else if (StringUtils.equals("email", searchOption)) {
			return QMember.member.email.like("%" + memberMngSearch + "%");
		}else if (StringUtils.equals("birth", searchOption)) {
			return QMember.member.birth.like("%" + memberMngSearch + "%");
		}else if (StringUtils.equals("join", searchOption)) {
			return QMember.member.join.like("%" + memberMngSearch + "%");
		}else if (StringUtils.equals("stat", searchOption) && Stat.회원.toString().contains(memberMngSearch)) {
			return QMember.member.stat.eq(Stat.회원);
		}else if (StringUtils.equals("stat", searchOption) && Stat.블랙.toString().contains(memberMngSearch)) {
			return QMember.member.stat.eq(Stat.블랙);
		}else if (StringUtils.equals("role", searchOption) && Role.ADMIN.toString().contains(memberMngSearch.toUpperCase())) {
			return QMember.member.role.eq(Role.ADMIN);
		}else if (StringUtils.equals("role", searchOption) && Role.USER.toString().contains(memberMngSearch.toUpperCase())) {
			return QMember.member.role.eq(Role.USER);
		}else if (StringUtils.equals("role", searchOption) && Role.COMPANY.toString().contains(memberMngSearch.toUpperCase())) {
			return QMember.member.role.eq(Role.COMPANY);
		}
		return null;
	}
	
}
