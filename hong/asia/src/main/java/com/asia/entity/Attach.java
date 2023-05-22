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
import lombok.ToString;

@Entity
@Table(name="attach")
@Getter
@Setter
@ToString
@SequenceGenerator(
        name="USER_SEQ_GEN7", //시퀀스 제너레이터 이름
        sequenceName="USER_SEQ7", //시퀀스 이름
        initialValue=1, //시작값
        allocationSize=1 //메모리를 통해 할당할 범위 사이즈
        )
public class Attach {
	
	@Id
	@Column(name="attach_num")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_SEQ_GEN7")
	private Long num;
	
	private String name;
	
	private String oriName;
	
	private String url;
	
	private String thumb;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="application_num")
	private Application application;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="voc_num")
	private Voc voc;
	
	public void updateAttach(String oriName, String name, String url) {
		this.oriName = oriName;
		this.name = name;
		this.url = url;
	}
}
