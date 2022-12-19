package com.korea.service;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
public class MailSendService {

	@Autowired
	private JavaMailSenderImpl mailSender;
	private int authNumber;
	
	public void makeRandomNumber() {
		 Random r = new Random();
		 int checkNum = r.nextInt(888888) + 111111;
		 System.out.println("인증번호" + checkNum);
		 authNumber = checkNum;
	}
	
	//이메일 보낼 양식
	public String joinEmail(String useremail) {
		
		makeRandomNumber();
		String setFrom = "gpwjddl9881@gmail.com";
		String toMail = useremail;
		
		String title = "회원 가입 인증 이메일 입니다.";
		String content = 
				"홈페이지를 방문해주셔서 감사합니다." +
						  "<br><br>" + 
			"인증번호는 " + authNumber + "입니다." +
			 "<br><br>" +
			"해당 인증번호를 확인란에 입력해주세요";
		mailSend(setFrom , toMail , title , content);
		
		return Integer.toString(authNumber);
	}

	public void mailSend(String setFrom , String toMail , String title , String content) {
		MimeMessage message = mailSender.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(content,true);
			mailSender.send(message);
			
		}catch(MessagingException e){
			e.printStackTrace();
		}
	}
	
	
	
}
