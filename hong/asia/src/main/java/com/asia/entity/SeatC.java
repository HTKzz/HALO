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

import com.asia.dto.SeatCDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "seatC")
@Getter
@Setter
@ToString
@SequenceGenerator(name = "USER_SEQ_GEN5", // 시퀀스 제너레이터 이름
		sequenceName = "USER_SEQ5", // 시퀀스 이름
		initialValue = 1, // 시작값
		allocationSize = 1 // 메모리를 통해 할당할 범위 사이즈
)
public class SeatC {

	@Id
	@Column(name = "seatC_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_SEQ_GEN5")
	private Long num;

	private String stat;

	private String seat;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "application_id")
	private Application application;

	public static SeatC createSeatC(SeatCDto SeatCDto) {
		SeatC seatC = new SeatC();
		seatC.setStat(SeatCDto.getStat());
		return seatC;
	}

	public void updateSeatC(String stat) {
		this.stat = stat;
	}

	public void updateSeat(String a) {
		this.stat = a;
	}

//	public void updateSeat(UpdateDto UpdateDto) {
//		this.stat = UpdateDto.this;
//	}
}
