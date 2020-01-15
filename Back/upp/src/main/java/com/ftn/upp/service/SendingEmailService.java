package com.ftn.upp.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.ftn.upp.repository.UserRepository;

@Service
public class SendingEmailService implements JavaDelegate{

	@Autowired
	IdentityService identityService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private Environment env;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		try 
		{
			sendNotificaitionAsync();
		}catch( Exception e )
		{
			System.out.println("Greska prilikom slanja emaila: " + e.getMessage());
		}
		
	}
	
	@Async
	public void sendNotificaitionAsync() throws MailException, InterruptedException, MessagingException {

		System.out.println("Slanje emaila...");
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

		String htmlMsg = "<h3>Pozdrav "+"Dragance"+"</h3><br> <p>Da biste aktivirali profil posetite  <a href=\"http://localhost:8080/user/aktivirajNalog/"+"borkovac.dragan96@gmail.com"+"\">link</a></p>";
		mimeMessage.setContent(htmlMsg, "text/html");
		helper.setTo("borkovac.dragan96@gmail.com");
		helper.setSubject("Verifikacija clanstva");
		helper.setFrom(env.getProperty("spring.mail.username"));
		javaMailSender.send(mimeMessage);
	
		System.out.println("Email poslat!");
	}
}
