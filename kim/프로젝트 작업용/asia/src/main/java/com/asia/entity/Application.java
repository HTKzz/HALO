package com.asia.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.asia.dto.ApplicationDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="application")
@Getter
@Setter
@ToString
@SequenceGenerator(name = "APPLICATION_SEQ_NUM",
				   sequenceName = "APPLICATION_SEQ",
				   initialValue = 1,
				   allocationSize = 1)
public class Application extends BaseEntity {
	
	// 프로그램 아이디
	@Id
	@Column(name="num")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="APPLICATION_SEQ_NUM")
	private Long num;
	
	// 프로그램명
	@Column(name="name", nullable = false)
	private String name;

	// 가격
	@Column(name="price", nullable = false)
	private Integer price;
	
	// 관람등급
	@Column(name="rat", nullable = false)
	private String rat;
	
	// 관람시간
	@Column(name="run")
	private Integer run;
	
	private String sdate;
	
	private String edate;
	
	private String udate;
	
	private String seatDetail;
	
	@Column(name="place")
	private String place;
	
	// 승인 상태
	private String approvalStatus;
	
	// 프로그램 상세정보
	@Lob
	@Column(name="detail", nullable = false)
	private String detail;
	
	// 프로그램 카테고리
	@Column(name="programCategory", nullable = false)
	private String programCategory;
	
//	@Enumerated(EnumType.STRING)
//	private ProgramCategory programCategory;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_num")
	private Member member;
	
	@OneToMany(mappedBy ="application", cascade=CascadeType.ALL)
	@ToString.Exclude
	private List<Attach> attach;
	
	@OneToMany(mappedBy ="application", cascade=CascadeType.ALL)
	@ToString.Exclude
	private List<SeatA> seatA;
	
	@OneToMany(mappedBy ="application", cascade=CascadeType.ALL)
	@ToString.Exclude
	private List<SeatB> seatB;
	
	@OneToMany(mappedBy ="application", cascade=CascadeType.ALL)
	@ToString.Exclude
	private List<SeatC> seatC;
	
	@OneToMany(mappedBy ="application", cascade=CascadeType.ALL)
	@ToString.Exclude
	private List<Reservation> reservation;
	
	// 프로그램 정보를 업데이트 해준다
	public void updateApplication(ApplicationDto applicationDto) {
		this.name = applicationDto.getName();
		this.price = applicationDto.getPrice();
		this.place = applicationDto.getPlace();
		this.rat = applicationDto.getRat();
		this.run = applicationDto.getRun();
		this.sdate = applicationDto.getSdate();
		this.edate = applicationDto.getEdate();
		this.seatDetail = applicationDto.getSeatDetail();
		this.detail = applicationDto.getDetail();
		this.programCategory = applicationDto.getProgramCategory();
	}
	
//	public static Application saveApplication(ApplicationDto applicationDto) {
//		Application application = new Application();
//		application.setName(applicationDto.getName());
//		application.setSdate(applicationDto.getSdate());
//		application.setEdate(applicationDto.getEdate());
//		application.setUdate(applicationDto.getUdate());
//		application.setSeatDetail(applicationDto.getSeatDetail());
//		
//		return application;
//	}

	
}
