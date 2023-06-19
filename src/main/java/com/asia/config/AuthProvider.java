package com.asia.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.asia.constant.Stat;
import com.asia.entity.Member;
import com.asia.service.MemberService;

@Component("authProvider")
public class AuthProvider implements AuthenticationProvider  {

    @Autowired
    MemberService memberService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String id = authentication.getName();
        /*String password = HashUtil.sha256(authentication.getCredentials().toString()); todo*/
        String password = String.valueOf(authentication.getCredentials());
        UserDetails user = memberService.loadUserByUsername(id);
        
        Member member = memberService.getMemDtl(id);

		// 이부분은 custom하게 구성하여 예외처리 진행
        if (null == user){
            throw new UsernameNotFoundException(id);
        }else if(member.getStat().equals(Stat.블랙)){
            throw new LockedException(id);
        }else if(member.getStat().equals(Stat.탈퇴)){
            throw new DisabledException(id);
        }else if(!user.getPassword().equals(password)){
            throw new BadCredentialsException(id);
        }

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

		// 로그인 인증 토큰
        UsernamePasswordAuthenticationToken token = 
        	new UsernamePasswordAuthenticationToken(id, password, user.getAuthorities());
        token.setDetails(user);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
