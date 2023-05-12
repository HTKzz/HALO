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

import com.asia.dto.SeatADto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="seatA")
@Getter
@Setter
@ToString
public class SeatA {
	
	@Id
	@Column(name="seatA_id")
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long num;
	
	private String stat;
	
	private String seat;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="application_id")
	private Application application;
	
	public static SeatA createSeatA(SeatADto SeatADto) {
		SeatA seatA = new SeatA();
		seatA.setStat(SeatADto.getStat());
		return seatA;
	}
	
	public void updateSeatA(String stat) {
		this.stat = stat;
	}
}
