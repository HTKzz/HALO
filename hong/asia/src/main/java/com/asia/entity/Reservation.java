package com.asia.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@ToString
@SequenceGenerator(name = "RESERVATION_SEQ_GEN", // 시퀀스 제너레이터 이름
		sequenceName = "RESERVATION_SEQ", // 시퀀스 이름
		initialValue = 1, // 시작값
		allocationSize = 1 // 메모리를 통해 할당할 범위 사이즈
)
public class Reservation extends BaseEntity {

	@Id
	@Column(name = "reservation_num")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RESERVATION_SEQ_GEN")
	private Long num;
	
	private String name;

	private Long cnt;

	private String seat;
	
	private String udate;
	
	private LocalDate rdate;

	private String place;
	
	private Long price;
	
	private String stat;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_num")
	private Member member;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="application_num")
	private Application application;

}
