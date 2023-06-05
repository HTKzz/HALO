 package com.asia.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.asia.constant.Role;
import com.asia.constant.Stat;
import com.asia.dto.MemberFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "pay")
@Getter
@Setter
@ToString
@SequenceGenerator(name = "PAY_SEQ_GEN", // 시퀀스 제너레이터 이름
		sequenceName = "PAY_SEQ", // 시퀀스 이름
		initialValue = 1, // 시작값
		allocationSize = 1 // 메모리를 통해 할당할 범위 사이즈
)
public class Pay extends BaseEntity {

	@Id
	@Column(name = "pay_num")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAY_SEQ_GEN")
	private Long num;
	
	@Column
	private String way;
	
	@Column
	private String udate;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "res_num")
	private Reservation Reservation;
	
	public static Pay createPay() {
		Pay pay = new Pay();
		pay.setWay("Card");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy년 MM월 dd일 HH시 mm분");
		String join1 = LocalDateTime.now().format(formatter); // LocalDateTime -> String 타입 변환하기
		pay.setUdate(join1);
		return pay;
	}
}
