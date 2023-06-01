package com.asia.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.asia.service.MemberService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	MemberService memberService;

	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
				.loginPage("/members/login")
				.defaultSuccessUrl("/")
				.usernameParameter("id")
				.failureUrl("/members/login/error")
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
				.logoutSuccessUrl("/");

		http.authorizeRequests()
				.mvcMatchers("/admin/**").hasRole("ADMIN")
				.mvcMatchers("/", "/members/**", "/mail/**", "/reservations/**", "/board/**", "/voc/**", "/notices/**", "/asia/**", "/pay/**").permitAll()
				.anyRequest().authenticated();

		http.exceptionHandling() // 인증되지 않은 사용자가 리소스에 접근하였을 때 수행되는 핸들러 등록
//      .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
//      .accessDeniedHandler(new CustomAccessDeniedHandler()); 
				.accessDeniedPage("/error_user");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
	}

	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/error");
	}
}