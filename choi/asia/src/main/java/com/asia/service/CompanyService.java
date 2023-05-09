package com.asia.service;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.asia.entity.Company;
import com.asia.repository.CompanyRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService implements UserDetailsService{
	
	private final CompanyRepository companyRepository;
	
	public Company saveCompany(Company company) {
		validateDuplicateCompany(company);
		return companyRepository.save(company);
	}

	private void validateDuplicateCompany(Company company) {
		
		Company findCompany = companyRepository.findById(company.getId());
		
		if (findCompany != null) {
			throw new IllegalStateException("이미 가입된 아이디입니다.");
		}
	}

	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		System.out.println("check"+id);
		Company company = companyRepository.findById(id);
		if (company == null) {
			throw new UsernameNotFoundException(id);
		}

		return User.builder().username(company.getId())
				.password(company.getPassword())
				.roles(company.getRole().toString())
				.build();
	}

	public boolean checkIdDuplicate(String id) {
		return companyRepository.existsById(id);
	}
	
	
}
