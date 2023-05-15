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

import com.asia.dto.AttachDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="Attach")
@Getter
@Setter
@ToString
@SequenceGenerator(name = "USER_SEQ_GEN3", // 시퀀스 제너레이터 이름
sequenceName = "USER_SEQ3", // 시퀀스 이름
initialValue = 1, // 시작값
allocationSize = 1 // 메모리를 통해 할당할 범위 사이즈
)
public class Attach {
	
	@Id
	@Column(name="attach_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_SEQ_GEN3")
	private Long num;
	
	private String name;
	private String oriname;
	private String url;
	private String thumb;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="board_id")
	private Board board;
	
	public static Attach addBoard(AttachDto attachDto) {
		
		Attach attach = new Attach();
		attach.setName(attachDto.getName());
		attach.setUrl(attachDto.getUrl());
		attach.setThumb(attachDto.getThumb());
		return attach;
	}
	
	public void updateAttach(String oriname, String name, String url) {
		
		this.oriname = oriname;
		this.name = name;
		this.url = url;
	}
	
}
