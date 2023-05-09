package com.asia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.asia.constant.Role;
import com.asia.constant.Stat;
import com.asia.dto.CompanyFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="Company")
@Getter
@Setter
@ToString
public class Company extends BaseEntity {
	
	@Id
	@Column(name="company_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long num;
	
	@Column
	private String id;
	
	@Column
	private String password;
	
	@Column
	private String name;
	
	@Enumerated(EnumType.STRING)
	private Stat stat;
	
	@Enumerated(EnumType.STRING)
	private Role role;

	public static Company createCompany(CompanyFormDto companyFormDto, PasswordEncoder passwordEncoder) {
		Company company = new Company();
		company.setId(companyFormDto.getId());
		String password = passwordEncoder.encode(companyFormDto.getPassword());
		company.setPassword(password);
		company.setName(companyFormDto.getName());
		company.setRole(Role.COMPANY);
		company.setStat(Stat.회원);
		return company;
	}
	
}
