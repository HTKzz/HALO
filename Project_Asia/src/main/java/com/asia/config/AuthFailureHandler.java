package com.asia.config;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		
		String msg = "Invalid Email or Password";
		
		if (exception instanceof AuthenticationServiceException) {
			msg = "존재하지 않는 사용자입니다.";
			
		} else if(exception instanceof BadCredentialsException) {
			msg = "아이디나 비밀번호가 일치하지 않습니다.";
			
		} else if(exception instanceof LockedException) {
			msg = "비활성화된 계정입니다.";
			
		} else if(exception instanceof DisabledException) {
			msg = "탈퇴된 계정입니다.";
			
		} else if(exception instanceof AccountExpiredException) {
			msg = "만료된 계정입니다.";
			
		} else if(exception instanceof CredentialsExpiredException) {
			msg = "비밀번호가 만료되었습니다.";
		}
		
		String errorMessage = URLEncoder.encode(msg, "UTF-8");
		setDefaultFailureUrl("/members/login?error=true&loginErrorMsg=" + errorMessage);
        super.onAuthenticationFailure(request, response, exception);
      
	}
}
