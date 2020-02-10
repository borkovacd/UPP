package com.ftn.upp.service.camunda;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.stereotype.Service;

import com.ftn.upp.model.ExtendedFormSubmissionDto;
import com.ftn.upp.model.Magazine;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.UserRepository;
import com.ftn.upp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


@Service
public class SendingCorrectedPDFToEditorService implements JavaDelegate {
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
		// TODO Auto-generated method stub
		  
		String usernameAssignedEditor = "";
		String emailAssignedEditor = "";
		
		usernameAssignedEditor = (String) runtimeService.getVariable(execution.getProcessInstanceId(), "dodeljeniUrednik");
		
		User assignedEditor = userRepository.findOneByUsername(usernameAssignedEditor);
		emailAssignedEditor = assignedEditor.getEmail();
		
		String processInstanceId = execution.getProcessInstanceId();
		
		 String fileName = (String) execution.getVariable("file_name");
	     byte[] decodedBytes = (byte[]) execution.getVariable("file_decoded");
	      
		try {
			sendNotificaitionAsync(processInstanceId, usernameAssignedEditor, emailAssignedEditor, fileName, decodedBytes);
		}catch( Exception e ) {
			System.out.println("Greska prilikom slanja emaila sa ispravljenim PDF-om dodeljenom uredniku: " + e.getMessage());
		}
	
	  
	}
	
	
	@Async
	public void sendNotificaitionAsync(String processInstanceId,
			   						   String username,
			   						   String email,
			   						   String fileName, 
			   						   byte[] decodedBytes) throws MailException, InterruptedException, MessagingException {

		//System.out.println("Email se šalje...");
		String content = "U prilgu se nalazi ispravljen PDF dokument koji je potrebno pregledati ";
		
	    ByteArrayOutputStream outputStream = null;

	    try {           
	        //construct the text body part
	        MimeBodyPart textBodyPart = new MimeBodyPart();
	        textBodyPart.setText(content);

	        //now write the PDF content to the output stream
	        //outputStream = new ByteArrayOutputStream();
	        writePdf(fileName, decodedBytes);
	        //byte[] bytes = outputStream.toByteArray();

	        //construct the pdf body part
	        DataSource dataSource = new ByteArrayDataSource(decodedBytes, "application/pdf");
	        MimeBodyPart pdfBodyPart = new MimeBodyPart();
	        pdfBodyPart.setDataHandler(new DataHandler(dataSource));
	        pdfBodyPart.setFileName(fileName);

	        //construct the mime multi part
	        MimeMultipart mimeMultipart = new MimeMultipart();
	        mimeMultipart.addBodyPart(textBodyPart);
	        mimeMultipart.addBodyPart(pdfBodyPart);

	        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			mimeMessage.setContent(mimeMultipart, "text/html");
			helper.setTo(email);
			helper.setSubject("Pregled ispravljenog PDF dokumenta od strane dodeljenog urednika");
			helper.setFrom(env.getProperty("spring.mail.username"));
			javaMailSender.send(mimeMessage);
		
			//System.out.println("Email sa ispravljenim PDFom namenjen dodeljenom uredniku je poslat!");
	                 
	    
	} catch(Exception ex) {
        ex.printStackTrace();
    } finally {
        //clean off
        if(null != outputStream) {
            try { outputStream.close(); outputStream = null; }
            catch(Exception ex) { }
        }
    }
		
		
	}
	
	public void writePdf(String fileName, byte[] decodedBytes) throws Exception {
		File file = new File("pdf_files/" + fileName);
		FileOutputStream fop = new FileOutputStream(file);
		fop.write(decodedBytes);
		fop.flush();
		fop.close();
	}
}
