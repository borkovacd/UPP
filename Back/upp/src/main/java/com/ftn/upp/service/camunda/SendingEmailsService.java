package com.ftn.upp.service.camunda;

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

import com.ftn.upp.model.ExtendedFormSubmissionDto;
import com.ftn.upp.model.FormSubmissionDto;
import com.ftn.upp.model.Magazine;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.UserRepository;
import com.ftn.upp.service.MagazineService;
import com.ftn.upp.service.UserService;

@Service
public class SendingEmailsService implements JavaDelegate{

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MagazineService magazineService;
	
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String usernameAuthor = "";
		String emailAuthor = "";
		String usernameChiefEditor = "";
		String emailChiefEditor = "";
		
		User author = userService.getCurrentUser();
		System.out.println("Autor: " + author.getUsername());
		usernameAuthor = author.getUsername();
		emailAuthor = author.getEmail();
		
		List<ExtendedFormSubmissionDto> chosenMagazineData = (List<ExtendedFormSubmissionDto>) execution.getVariable("chosenMagazine");
		for(ExtendedFormSubmissionDto item: chosenMagazineData) {
			 String fieldId=item.getFieldId();
			 if(fieldId.equals("casopisi")){
				  List<Magazine> allMagazines = magazineService.findAll();
				  for(Magazine m : allMagazines){
					  for(String selected: item.getCategories()){
						  String idM = m.getId().toString();
						  if(idM.equals(selected)) {
							  User chiefEditor = m.getMainEditor();
							  System.out.println("Glavni urednik: " + chiefEditor.getUsername());
							  usernameChiefEditor = chiefEditor.getUsername();
							  emailChiefEditor = chiefEditor.getEmail();
						  }
					  }
				  }
			 }
		}
		
		
		String processInstanceId = execution.getProcessInstanceId();
		try 
		{
			sendNotificaitionAsync(processInstanceId, usernameAuthor, emailAuthor);
		}catch( Exception e )
		{
			System.out.println("Greska prilikom slanja emaila autoru: " + e.getMessage());
		}
		try 
		{
			sendNotificaitionAsync(processInstanceId, usernameChiefEditor, emailChiefEditor);
		}catch( Exception e )
		{
			System.out.println("Greska prilikom slanja emaila glavnom uredniku: " + e.getMessage());
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
	
		System.out.println("Email notifikacija je uspešno poslata!");
	}
}
