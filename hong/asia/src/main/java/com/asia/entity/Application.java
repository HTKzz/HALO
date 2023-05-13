package com.asia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Application {
	
	@Id
	@Column(name="application_id")
	@GeneratedValue(strategy=GenerationType.TABLE)
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
