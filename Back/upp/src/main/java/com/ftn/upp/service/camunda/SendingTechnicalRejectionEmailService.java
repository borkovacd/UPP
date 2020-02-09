package com.ftn.upp.service.camunda;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ftn.upp.model.Article;
import com.ftn.upp.model.ExtendedFormSubmissionDto;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.ArticleRepository;
import com.ftn.upp.repository.UserRepository;
import com.ftn.upp.service.MagazineService;
import com.ftn.upp.service.UserService;

@Service
public class SendingTechnicalRejectionEmailService implements JavaDelegate{

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
	
	@Autowired 
	private ArticleRepository articleRepository;
	
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String usernameAuthor = "";
		String emailAuthor = "";
		
	    List<ExtendedFormSubmissionDto> articleData = (List<ExtendedFormSubmissionDto>)execution.getVariable("article_data");
	    
	    String articleTitle = "";
		for (ExtendedFormSubmissionDto formField : articleData) {
			if(formField.getFieldId().equals("naslov")) {
				articleTitle = formField.getFieldValue();
			}
		}
		  
		Article article = articleRepository.findOneByTitle(articleTitle);
		User author = userRepository.findOneByUsername(article.getAuthor().getUsername());
		usernameAuthor = author.getUsername();
		emailAuthor = author.getEmail();
		
		
		String processInstanceId = execution.getProcessInstanceId();
		try 
		{
			sendNotificaitionAsync(processInstanceId, usernameAuthor, emailAuthor);
		}catch( Exception e )
		{
			System.out.println("Greska prilikom slanja emaila autoru: " + e.getMessage());
		}
		
		
	}
	
	@Async
	public void sendNotificaitionAsync(String processInstanceId,
									   String username,
									   String email) throws MailException, InterruptedException, MessagingException {

		System.out.println("Email se šalje...");
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

		String htmlMsg = "<h4>Obavestavamo Vas da je rad koji ste prijavili odbijen iz tehnickih razloga.</h4>";
		mimeMessage.setContent(htmlMsg, "text/html");
		helper.setTo(email);
		helper.setSubject("Obaveštenje o odbijanju rada");
		helper.setFrom(env.getProperty("spring.mail.username"));
		javaMailSender.send(mimeMessage);
	
		System.out.println("Email notifikacija o odbijanju iz tehnickih razloga je uspešno poslata!");
	}
}


