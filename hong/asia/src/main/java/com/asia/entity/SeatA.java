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

import com.asia.dto.SeatADto;
import com.asia.dto.UpdateDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "seatA")
@Getter
@Setter
@ToString
@SequenceGenerator(name = "USER_SEQ_GEN3", // 시퀀스 제너레이터 이름
		sequenceName = "USER_SEQ3", // 시퀀스 이름
		initialValue = 1, // 시작값
		allocationSize = 1 // 메모리를 통해 할당할 범위 사이즈
)
public class SeatA {

	@Id
	@Column(name = "seatA_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_SEQ_GEN3")
	private Long num;

	private String stat;

	private String seat;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "application_id")
	private Application application;

	public static SeatA createSeatA(SeatADto SeatADto) {
		SeatA seatA = new SeatA();
		seatA.setStat(SeatADto.getStat());
		return seatA;
	}

	public void updateSeatA(String stat) {
		this.stat = stat;
	}

	public void updateSeat(String a) {
		this.stat = a;
	}

//	public void updateSeat(UpdateDto UpdateDto) {
//		this.stat = UpdateDto.this;
//	}
}
