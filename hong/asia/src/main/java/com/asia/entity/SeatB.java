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

import com.asia.dto.SeatBDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "seatB")
@Getter
@Setter
@ToString
@SequenceGenerator(name = "USER_SEQ_GEN4", // 시퀀스 제너레이터 이름
		sequenceName = "USER_SEQ4", // 시퀀스 이름
		initialValue = 1, // 시작값
		allocationSize = 1 // 메모리를 통해 할당할 범위 사이즈
)
public class SeatB {

	@Id
	@Column(name = "seatB_num")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_SEQ_GEN4")
	private Long num;

	private String stat;

	private String seat;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "application_num")
	private Application application;

	public static SeatB createSeatB(SeatBDto SeatBDto) {
		SeatB seatB = new SeatB();
		seatB.setStat(SeatBDto.getStat());
		return seatB;
	}

	public void updateSeatB(String stat) {
		this.stat = stat;
	}

	public void updateSeat(String a) {
		this.stat = a;
	}
}
