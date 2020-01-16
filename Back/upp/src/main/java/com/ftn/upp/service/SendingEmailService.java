package com.ftn.upp.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.ftn.upp.model.FormSubmissionDto;
import com.ftn.upp.repository.UserRepository;

@Service
public class SendingEmailService implements JavaDelegate{

	@Autowired
	UserRepository userRepository;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private Environment env;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String username = "";
		String email = "";
		
		List<FormSubmissionDto> registration = (List<FormSubmissionDto>) execution.getVariable("registration");
		
		for (FormSubmissionDto formField : registration) {
			if(formField.getFieldId().equals("email")) {
				email = formField.getFieldValue();
			}
			if(formField.getFieldId().equals("username")) {
				username = formField.getFieldValue();
			}
		}
		
		String processInstanceId = execution.getProcessInstanceId();
		try 
		{
			sendNotificaitionAsync(processInstanceId, username, email);
		}catch( Exception e )
		{
			System.out.println("Greska prilikom slanja emaila: " + e.getMessage());
		}
		
	}
	
	@Async
	public void sendNotificaitionAsync(String processInstanceId,
									   String username,
									   String email) throws MailException, InterruptedException, MessagingException {

		System.out.println("Email se šalje...");
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

		String htmlMsg = "<h2>Samo još jedan korak...</h2><br> <h3>"+username+"</h3><br><p>Da biste aktivirali profil posetite  <a href=\"http://localhost:4200/activation-page"+"/"+processInstanceId+"/"+1+"\">link</a></p>";
		mimeMessage.setContent(htmlMsg, "text/html");
		helper.setTo(email);
		helper.setSubject("Verifikacija clanstva");
		helper.setFrom(env.getProperty("spring.mail.username"));
		javaMailSender.send(mimeMessage);
	
		System.out.println("Email sa linkom za povrdu je uspešno poslat!");
	}
}
