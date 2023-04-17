package com.bookshop01.mail.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookshop01.mail.service.MailService;

@Controller("emailController")
@RequestMapping(value = "/email")
@EnableAsync
public class MailController {
	@Autowired
	private MailService mailService;
	private Map<String, String> codeMap = new HashMap<String, String>();

	@RequestMapping(value = "/sendEmail.do", method = RequestMethod.POST)
	public ResponseEntity sendSimpleMail(@RequestParam("email") String email, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResponseEntity resEntity = null;
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		boolean emailCheck = isValidEmail(email);
		if(emailCheck == false) {
			String result = "False";
			resEntity = new ResponseEntity(result, HttpStatus.OK);
			return resEntity;
		}
		String checkCode = String.format("%05d", (int)(Math.random() * 100000));
		codeMap.put(email, checkCode);
		mailService.sendMail(email, "BookShop 인증메일", "인증번호 : " + checkCode);
		String result = "Success";
		resEntity = new ResponseEntity(result, HttpStatus.OK);
		return resEntity;
	}

	@RequestMapping(value = "/codeCheck.do", method = RequestMethod.POST)
	public ResponseEntity codeCheck(@RequestParam("code") String code, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResponseEntity resEntity = null;
		String result = "False";
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String[] key = code.split(",");
		if(codeMap.get(key[0]).equals(key[1]))
			result = "Success";
		resEntity = new ResponseEntity(result, HttpStatus.OK);
		return resEntity;
	}

	private static boolean isValidEmail(String email) {
		boolean err = false;
		String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(email);
		if (m.matches()) {
			err = true;
		}
		return err;
	}
}