package com.asia.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
@SequenceGenerator(name = "USER_SEQ_GEN2", // 시퀀스 제너레이터 이름
sequenceName = "USER_SEQ2", // 시퀀스 이름
initialValue = 1, // 시작값
allocationSize = 1 // 메모리를 통해 할당할 범위 사이즈
)
public class Board extends BaseEntity {
	
	@Id
	@Column(name="board_num")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_SEQ_GEN2")
	private Long num;
	
	private String name;
	
	private String content;
	
	private LocalDate d_date;
	
	@Column(columnDefinition = "integer default 0")
	private Integer cnt;
	
    private Long originNo;
    
    private Long groupOrd;
 
    private Long groupLayer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_num")
	private Member member;
	
	@OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<Attach> attachList;
	
	@Enumerated(EnumType.STRING)
	private Stat stat;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	//새 게시글 쓰기
	public static Board addBoard(BoardDto boardDto) {
		
		Board board = new Board();
		board.setName(boardDto.getName());
		board.setContent(boardDto.getContent());
		board.setOriginNo(boardDto.getOriginNo());
		board.setCnt(board.cnt);
		board.getRole();
		return board;
	}
	
	//글 수정하기
	public void updateBoard(BoardDto boardDto) {
		
		this.num = boardDto.getNum();
		this.name = boardDto.getName();
		this.content = boardDto.getContent();
	}
	
	//글 삭제하기
	public void deleteBoard(BoardDto boardDto) {
		
		this.num = boardDto.getNum();
	}
}
