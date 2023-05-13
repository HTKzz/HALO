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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="Attach")
@Getter
@Setter
@ToString
public class Attach {
	
	@Id
	@Column(name="attach_id")
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long num;
	
	private String name;
	private String oriname;
	private String url;
	private String thumb;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="board_id")
	private Board board;
	
}
