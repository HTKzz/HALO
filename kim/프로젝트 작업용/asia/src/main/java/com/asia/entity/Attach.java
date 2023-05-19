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

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Attach")
@Getter
@Setter
@SequenceGenerator(name = "ATTACH_SEQ_NUM",
				   sequenceName = "ATTACH_SEQ",
				   initialValue = 1,
				   allocationSize = 1)
public class Attach extends BaseEntity {

	@Id
	@Column(name = "num")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ATTACH_SEQ_NUM")
	private Long num;

	@Column(name = "name")
	private String name; // 이미지 파일명
	
	@Column(name = "oriname")
	private String oriName; // 원본 이미지 파일명
	
	@Column(name = "url")
	private String url; // 이미지 조회 경로
	
	@Column(name = "repimgYn")
	private String repimgYn; // 대표 이미지 여부

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_num")
	private Application application;

	public void updateAttach(String oriName, String name, String url) {
		this.oriName = oriName;
		this.name = name;
		this.url = url;
	}

}
