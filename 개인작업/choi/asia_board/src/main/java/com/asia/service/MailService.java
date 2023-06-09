package com.asia.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asia.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
@EnableAsync
public class MailService {

	// String에서 제공하는 MailSender
	private final JavaMailSenderImpl mailSender;
	private Map<String, String> checkCode = new HashMap<>();

	// 인증번호 난수 생성
	public static String createKey() {
		StringBuffer key = new StringBuffer();
		Random rnd = new Random();

		for (int i = 0; i < 8; i++) {
			int index = rnd.nextInt(3);
			switch (index) {
			case 0:
				key.append((char) ((int) (rnd.nextInt(26)) + 97));
				break;
			case 1:
				key.append((char) ((int) (rnd.nextInt(26)) + 65));
				break;
			case 2:
				key.append((rnd.nextInt(10)));
				break;
			}
		}
		return key.toString();
	}

	// 회원 가입시 이메일 발송
	@Async // Async는 return 값이 void가 됨.
	public String sendAuthMail(String email) throws MessagingException {
		String authKey = createKey();
		checkCode.put("code", authKey);
		MimeMessage mailMessage = mailSender.createMimeMessage();
		String mailContent = "인증번호 : " + authKey;
		mailMessage.setSubject("국제아시아문화전당 회원가입 인증메일", "utf-8");
		mailMessage.setText(mailContent, "utf-8", "html");
		mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
		mailSender.send(mailMessage); // <--회원가입시 email란에 입력한 이메일주소로 인증메일이 보내진다.
		
		return authKey;
	}

	// 일반 회원비밀번호 찾기
	@Async // Async는 return 값이 void가 됨. CompletableFuture객체를 이용하면 return값을 보낼수있음
	public CompletableFuture<String> sendFindPwMail(String email, Member member) throws MessagingException {
		String authKey = createKey();
		MimeMessage mailMessage = mailSender.createMimeMessage();
		String mailContent = member.getId() + " 님의 임시 비밀번호 : " + authKey;
		mailMessage.setSubject("국제아시아문화전당 메일", "utf-8");
		mailMessage.setText(mailContent, "utf-8", "html");
		mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
		mailSender.send(mailMessage); // <--회원가입시 email란에 입력한 이메일주소로 인증메일이 보내진다.

		return CompletableFuture.completedFuture(authKey);
	}

	// 인증번호 확인
	public boolean checkCode(String emailcode) {
		boolean check = false;
		if (checkCode.get("code").equals(emailcode)) {
			check = true;
		} else {
			check = false;
		}
		return check;
	}

	// 일반회원 아이디 찾기 이메일
	@Async
	public String sendFindIdMail(String email, Member member) throws MessagingException {
		MimeMessage mailMessage = mailSender.createMimeMessage();
		String mailContent = "아이디 : " + member.getId();
		mailMessage.setSubject("국제아시아문화전당 메일", "utf-8");
		mailMessage.setText(mailContent, "utf-8", "html");
		mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
		mailSender.send(mailMessage); // <--회원가입시 email란에 입력한 이메일주소로 인증메일이 보내진다.

		return member.getId();
	}
//	//기업 회원 비밀번호 찾기 
//	@Async 
//	public CompletableFuture<String> sendFindPwMail(String email, Company company) throws MessagingException{
//	   String authKey = createKey();
//	    MimeMessage mailMessage = mailSender.createMimeMessage();
//	    String mailContent = company.getCom() +" 님의 임시 비밀번호 : "+ authKey ;    
//	        mailMessage.setSubject("여수세계섬박람회 메일", "utf-8"); 
//	        mailMessage.setText(mailContent, "utf-8", "html");  
//	        mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
//	        mailSender.send(mailMessage); // <--회원가입시 email란에 입력한 이메일주소로 인증메일이 보내진다.
//	        
//	      return CompletableFuture.completedFuture(authKey);
//	    }

//	@Async //Async는 return 값이 void가 됨.
//	public void eventSendhMail(String email, String name) throws MessagingException{
//	    MimeMessage mailMessage = mailSender.createMimeMessage();
//	    String mailContent = name +"님은 이벤트에 당첨 되셨습니다 축하드립니다!!";    
//	        mailMessage.setSubject("여수세계섬박람회 이벤트 당첨 메일", "utf-8"); 
//	        mailMessage.setText(mailContent, "utf-8", "html");  
//	        mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
//	        mailSender.send(mailMessage); // <--회원가입시 email란에 입력한 이메일주소로 인증메일이 보내진다.
//
//	    }

}
