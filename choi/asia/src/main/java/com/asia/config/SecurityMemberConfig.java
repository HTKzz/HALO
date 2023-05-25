//package com.asia.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import com.asia.service.CompanyService;
//import com.asia.service.MemberService;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityMemberConfig extends WebSecurityConfigurerAdapter {
//	
//	@Autowired
//	MemberService memberService;
//	
//	@Autowired
//	CompanyService companyService;
//	
//	
////	protected void configure(HttpSecurity http) throws Exception {
////		http.formLogin()
////				.loginPage("/members/login")
////				.defaultSuccessUrl("/")
////				.usernameParameter("id")
////				.failureUrl("/members/login/error")
////				.and()
////				.logout()
////				.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
////				.logoutSuccessUrl("/") 
////				
////		;
////		
////		http.authorizeRequests()
////			.mvcMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
////			.mvcMatchers("/admin/**").hasRole("ADMIN")
////			.anyRequest().authenticated()
////		;
////		
////		http.exceptionHandling()
////			.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
////		;
////		
////	}
//	
//	
//	protected void configure(HttpSecurity http) throws Exception {
//		
//		
//		http
//				.formLogin()
//				.loginPage("/members/login")
//				.defaultSuccessUrl("/")
//				.usernameParameter("id")
//				.failureUrl("/members/login/error")
//				.and()
//				.formLogin()
//				.loginPage("/companys/login")
//				.defaultSuccessUrl("/")
//				.usernameParameter("id")
//				.failureUrl("/companys/login/error")
//				.and()
//				.logout()
//				.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
//				.logoutSuccessUrl("/") 
//				.and()
//				.logout()
//				.logoutRequestMatcher(new AntPathRequestMatcher("/companys/logout"))
//				.logoutSuccessUrl("/") 
//		;
//		
//		http
//				.authorizeRequests()
//				.mvcMatchers("/", "/members/**", "/companys/**", "/item/**", "/images/**").permitAll()
//				.mvcMatchers("/admin/**").hasRole("ADMIN")
//				.anyRequest()
//				.authenticated()
//		;
//		
//		http
//				.exceptionHandling()
//				.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
//		;
//		
//	}
//	
//	
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//	
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth
//				.userDetailsService(memberService)
//				.passwordEncoder(passwordEncoder());
//		
//		auth
//				.userDetailsService(companyService)
//				.passwordEncoder(passwordEncoder());
//	}
//	
//	public void configure(WebSecurity web) throws Exception {
//		web
//				.ignoring()
//				.antMatchers("/css/**", "/js/**", "/img/**");
//	}
//}