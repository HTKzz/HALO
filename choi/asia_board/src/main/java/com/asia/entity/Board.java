package com.asia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.asia.constant.Role;
import com.asia.constant.Stat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="board")
@Getter
@Setter
@ToString
public class Board extends BaseEntity {
	
	@Id
	@Column(name="board_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long num;
	
	private String name;
	
	private String content;
	
	private Integer count;
	
	@Enumerated(EnumType.STRING)
	private Stat stat;
	
	@Enumerated(EnumType.STRING)
	private Role role;
}
