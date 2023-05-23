package com.asia.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.asia.constant.ProgramCategory;
import com.asia.dto.ApplicationDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="Application")
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
	
	// 프로그램 상세정보
	@Lob
	@Column(name="detail", nullable = false)
	private String detail;
	
	@Enumerated(EnumType.STRING)
	private ProgramCategory programCategory;
	
	@OneToMany(mappedBy ="application", cascade=CascadeType.ALL)
	@ToString.Exclude
	private List<Attach> attach;
	
	// 프로그램 정보를 업데이트 해준다
	public void updateApplication(ApplicationDto applicationDto) {
		this.name = applicationDto.getName();
		this.price = applicationDto.getPrice();
		this.rat = applicationDto.getRat();
		this.run = applicationDto.getRun();
		this.detail = applicationDto.getDetail();
		this.programCategory = applicationDto.getProgramCategory();
	}

	
}
