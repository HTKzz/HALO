package com.asia.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.asia.constant.Role;
import com.asia.constant.Stat;
import com.asia.dto.BoardDto;

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
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long num;
	
	private String name;
	
	private String content;
	
	private Date d_date;
	
	private Integer cnt;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="member_num")
//	private Member member;
	
	@Enumerated(EnumType.STRING)
	private Stat stat;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	
	//새 게시글 쓰기
	public static Board addBoard(BoardDto boardDto) {
		
		
		Board board = new Board();
		board.setName(boardDto.getName());
		board.setContent(boardDto.getContent());
		board.setD_date(boardDto.getD_date());
		board.getRole();
		return board;
	}
	
	
}
