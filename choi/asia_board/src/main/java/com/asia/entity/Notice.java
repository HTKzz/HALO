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
import com.asia.dto.NoticeDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="notice")
@Getter
@Setter
@ToString
@SequenceGenerator(name = "USER_SEQ_GEN2", // 시퀀스 제너레이터 이름
sequenceName = "USER_SEQ2", // 시퀀스 이름
initialValue = 1, // 시작값
allocationSize = 1 // 메모리를 통해 할당할 범위 사이즈
)
public class Notice extends BaseEntity {
	
	@Id
	@Column(name="notice_num")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_SEQ_GEN2")
	private Long num;
	
	private String name;
	
	private String content;
	
	private LocalDate d_date;
	
	@Column(columnDefinition = "integer default 0")
	private Integer cnt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_num")
	private Member member;
	
	@OneToMany(mappedBy = "notice", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<Attach> attachList;
	
	@Enumerated(EnumType.STRING)
	private Stat stat;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	//새 게시글 쓰기
	public static Notice addNotice(NoticeDto noticeDto) {
		
		Notice notice = new Notice();
		notice.setName(noticeDto.getName());
		notice.setContent(noticeDto.getContent());
		notice.setCnt(notice.cnt);
		notice.getRole();
		return notice;
	}
	
	//글 수정하기
	public void updateNotice(NoticeDto noticeDto) {
		
		this.num = noticeDto.getNum();
		this.name = noticeDto.getName();
		this.content = noticeDto.getContent();
	}
	
	//글 삭제하기
	public void deleteNotice(NoticeDto noticeDto) {
		
		this.num = noticeDto.getNum();
	}
}
