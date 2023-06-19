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
import javax.persistence.Lob;
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
@SequenceGenerator(name = "NOTICE_SEQ_GEN", // 시퀀스 제너레이터 이름
sequenceName = "NOTICE_SEQ", // 시퀀스 이름
initialValue = 1, // 시작값
allocationSize = 1 // 메모리를 통해 할당할 범위 사이즈
)
public class Notice extends BaseEntity {
	
	@Id
	@Column(name="notice_num")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NOTICE_SEQ_GEN")
	private Long num;
	
	private String name;
	
	@Lob
	private String content;
	
	private LocalDate d_date;
	
	@Column(nullable = false)
	private int cnt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_num")
	private Member member;
	
	@OneToMany(mappedBy = "notice", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<Attach> attach;
	
	//글 수정하기
	public void updateNotice(NoticeDto noticeDto) {
		this.name = noticeDto.getName();
		this.content = noticeDto.getContent();
	}
}
