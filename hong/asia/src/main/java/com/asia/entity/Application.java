package com.asia.entity;

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

import com.asia.dto.ApplicationDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="application")
@Getter
@Setter
@ToString
@SequenceGenerator(
        name="USER_SEQ_GEN1", //시퀀스 제너레이터 이름
        sequenceName="USER_SEQ1", //시퀀스 이름
        initialValue=1, //시작값
        allocationSize=1 //메모리를 통해 할당할 범위 사이즈
        )
public class Application {
	
	@Id
	@Column(name="application_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_SEQ_GEN1")
	private Long num;
	
	private String name;
	
	private String startdate;
	
	private String enddate;
	
	private String udate;
	
	private String seatDetail;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;
	
	public static Application saveApplication(ApplicationDto applicationDto) {
		Application application = new Application();
		application.setName(applicationDto.getName());
		application.setStartdate(applicationDto.getStartdate());
		application.setEnddate(applicationDto.getEnddate());
		application.setUdate(applicationDto.getUdate());
		application.setSeatDetail(applicationDto.getSeatDetail());
		
		return application;
	}
}
