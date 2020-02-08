package com.ftn.upp.service.camunda;


import java.io.ByteArrayOutputStream;
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
import com.ftn.upp.service.MagazineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


@Service
public class SendingPDFToEditorService implements JavaDelegate{
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private MagazineService magazineService ;

	// KOMENTAR: PRILOZITI PDF U EMAIL-U
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		  
		String usernameChiefEditor = "";
		String emailChiefEditor = "";
		
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
		try {
			sendNotificaitionAsync(processInstanceId, usernameChiefEditor, emailChiefEditor);
		}catch( Exception e ) {
			System.out.println("Greska prilikom slanja emaila sa PDF-om glavnom uredniku: " + e.getMessage());
		}
	
	  
	}
	
	
	@Async
	public void sendNotificaitionAsync(String processInstanceId,
			   						   String username,
			   						   String email) throws MailException, InterruptedException, MessagingException {

		System.out.println("Email se šalje...");
		String content = "U prilgu se nalazi PDF dokument koji je potrebno pregledati ";
		
	    ByteArrayOutputStream outputStream = null;

	    try {           
	        //construct the text body part
	        MimeBodyPart textBodyPart = new MimeBodyPart();
	        textBodyPart.setText(content);

	        //now write the PDF content to the output stream
	        outputStream = new ByteArrayOutputStream();
	        //writePdf(outputStream);
	        byte[] bytes = outputStream.toByteArray();

	        //construct the pdf body part
	        DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
	        MimeBodyPart pdfBodyPart = new MimeBodyPart();
	        pdfBodyPart.setDataHandler(new DataHandler(dataSource));
	        pdfBodyPart.setFileName("test.pdf");

	        //construct the mime multi part
	        MimeMultipart mimeMultipart = new MimeMultipart();
	        mimeMultipart.addBodyPart(textBodyPart);
	        mimeMultipart.addBodyPart(pdfBodyPart);

	        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			mimeMessage.setContent(mimeMultipart, "text/html");
			helper.setTo(email);
			helper.setSubject("Pregled PDF dokumenta od strane glavnog urednika");
			helper.setFrom(env.getProperty("spring.mail.username"));
			javaMailSender.send(mimeMessage);
		
			System.out.println("Email sa PDFom namenjen glavnom uredniku je poslat!");
	                 
	    
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
	
	
	/*
	public void writePdf(OutputStream outputStream) throws Exception {
	    Document document = new Document();
	    PdfWriter.getInstance(document, outputStream);
	    document.open();
	    Paragraph paragraph = new Paragraph();
	    paragraph.add(new Chunk("hello!"));
	    document.add(paragraph);
	    document.close();
	}
	*/


}
