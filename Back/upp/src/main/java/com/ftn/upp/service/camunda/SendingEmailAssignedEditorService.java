package com.ftn.upp.service.camunda;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ftn.upp.model.ExtendedFormSubmissionDto;
import com.ftn.upp.model.Magazine;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.UserRepository;
import com.ftn.upp.service.MagazineService;
import com.ftn.upp.service.UserService;

@Service
public class SendingEmailAssignedEditorService implements JavaDelegate{

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String usernameAssignedEditor = "";
		String emailAssignedEditor = "";
		
		usernameAssignedEditor = (String) runtimeService.getVariable(execution.getProcessInstanceId(), "dodeljeniUrednik");
		
		User assignedEditor = userRepository.findOneByUsername(usernameAssignedEditor);
		emailAssignedEditor = assignedEditor.getEmail();
		
		String processInstanceId = execution.getProcessInstanceId();
		try 
		{
			sendNotificaitionAsync(processInstanceId, usernameAssignedEditor, emailAssignedEditor);
		}catch( Exception e )
		{
			System.out.println("Greska prilikom slanja emaila dodeljenom uredniku: " + e.getMessage());
		}
		
		
	}
	
	@Async
	public void sendNotificaitionAsync(String processInstanceId,
									   String username,
									   String email) throws MailException, InterruptedException, MessagingException {

		System.out.println("Email se šalje...");
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

		String htmlMsg = "<h2>Obavestavamo Vas da je prijavljen novi rad u sistem.</h2>";
		mimeMessage.setContent(htmlMsg, "text/html");
		helper.setTo(email);
		helper.setSubject("Notifikacija o prijavi novog rada");
		helper.setFrom(env.getProperty("spring.mail.username"));
		javaMailSender.send(mimeMessage);
	
		System.out.println("Email notifikacija dodeljenom uredniku je uspešno poslata!");
	}
}

