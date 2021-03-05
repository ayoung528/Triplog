package com.ssafy.trip.service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class EmailValidationService {
	private static final Logger logger = LoggerFactory.getLogger(EmailValidationService.class);
	
	@Autowired
    JavaMailSender javaMailSender;
	
	@Async
	public void sendEmail(String email) {
		MimeMessage simpleMessage = javaMailSender.createMimeMessage();
        String memberMail = email;
        try {
			simpleMessage.addRecipient(RecipientType.TO, new InternetAddress(memberMail));
			simpleMessage.setSubject("TRIP 회원가입 인증 메일");
//			simpleMessage.setText(new StringBuffer().append("<h1>회원가입 인증메일입니다.</h1>")
//					.append("<p>밑의 링크를 클릭하면 메일이 인증 됩니다.</p>")
//					.append("<a href='http://localhost:8080/emailValid/auth/"+memberMail)
//					.append("' target=blank>메일 인증 링크</a>")
//					.toString(), "UTF-8", "html");
			simpleMessage.setText(new StringBuffer().append("<h1 style=\"color:cyan\">TRIPLOG</h1>")
					.append("<hr />").append("<h3>안녕하세요. TRIPLOG입니다.</h3>\r\n" + 
							"    <h3>TRIPLOG계정 사용을 위해 이메일 주소 인증이 필요합니다.</h3>\r\n" + 
							"    <h3>TRIPLOG 계정 :\r\n" + memberMail + "</h3>"+ "<br />" +
							"    <p>아래의 버튼을 클릭하여 이메일 주소 인증을 완료하면 해당 이메일 주소를 TRIPLOG 계정으로 사용할 수 있습니다.</p>\r\n" + 
							"    <p>만일 TRIPLOG 계정을 등록하지 않았는데 본 메일을 받았다면 아래 버튼을 클릭하지 마세요.</p>" + "<br />")
					.append("<button style=\"background-color: #fff;\r\n" + 
							"	border-color: #1c98ad;\r\n" + 
							"	color: #1c98ad; \"><a style=\"text-decoration: none; color:black;\" href=\"http://i3b207.p.ssafy.io:8080/emailValid/auth/"+memberMail)
					.append("\" target=blank>TRIPLOG시작하기</a></button>")
					.toString(), "UTF-8", "html");
			javaMailSender.send(simpleMessage);
			logger.info("async send email to : " + email);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Async
	public void sendSearchEmail(String email, String code) {
		MimeMessage simpleMessage = javaMailSender.createMimeMessage();
        String memberMail = email;
        try {
			simpleMessage.addRecipient(RecipientType.TO, new InternetAddress(memberMail));
			simpleMessage.setSubject("TRIP 비밀번호 찾기 및 변경 서비스 메일");
//			simpleMessage.setText(new StringBuffer().append("<h1>회원가입 인증메일입니다.</h1>")
//					.append("<p>밑의 링크를 클릭하면 메일이 인증 됩니다.</p>")
//					.append("<a href='http://localhost:8080/emailValid/auth/"+memberMail)
//					.append("' target=blank>메일 인증 링크</a>")
//					.toString(), "UTF-8", "html");
			simpleMessage.setText(new StringBuffer().append("<h1 style=\"color:cyan\">TRIPLOG</h1>")
					.append("<hr />").append("<h3>안녕하세요. TRIPLOG입니다.</h3>\r\n" + 
							"    <h3>TRIPLOG 비밀번호 찾기 및 변경 서비스입니다.</h3>\r\n" + 
							"    <h3>TRIPLOG 계정 :\r\n" + memberMail + "</h3>"+ "<br />" +
							"    <p>아래의 버튼을 클릭하여 해당 링크에서 위 TRIPLOG 계정의 비밀번호를 변경 후 사용하시기 바랍니다.</p>\r\n" + 
							"    <p>만일 TRIPLOG 계정을 찾기 서비스를 이용하지  않았는데 본 메일을 받았다면 아래 버튼을 클릭하지 마세요.</p>" + "<br />")
					.append("<button style=\"background-color: #fff;\r\n" + 
							"	border-color: #1c98ad;\r\n" + 
							"	color: #1c98ad; \"><a style=\"text-decoration: none; color:black;\" href=\"http://i3b207.p.ssafy.io/auth/search/?code="+code)
					.append("\" target=blank>TRIPLOG 계정 찾기</a></button>")
					.toString(), "UTF-8", "html");
			javaMailSender.send(simpleMessage);
			logger.info("async send email to : " + email);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
