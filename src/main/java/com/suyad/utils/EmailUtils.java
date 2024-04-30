package com.suyad.utils;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;



@Component
public class EmailUtils
{
	@Autowired
	private JavaMailSender mailSender;
	
	public boolean sendEmail(String to,String sub,String body)
	{
		boolean isSent = false;
		try
		{
			MimeMessage mimeMsg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMsg);// use true to Send Multipart(attached,files) with mail
			helper.setTo(to);
			helper.setSubject(sub);
			helper.setText(body, true);
			
			mailSender.send(mimeMsg);
			isSent = true;
		}
		catch(Exception e)
		{
			isSent = false;
			e.printStackTrace();
		}
		return isSent;
	}
}
