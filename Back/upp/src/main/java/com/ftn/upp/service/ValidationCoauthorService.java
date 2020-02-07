package com.ftn.upp.service;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import com.ftn.upp.model.FormSubmissionDto;

@Service
public class ValidationCoauthorService implements JavaDelegate {
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		  
		  boolean hasErrors = false;
		  
		  List<FormSubmissionDto> coauthorData = (List<FormSubmissionDto>)execution.getVariable("coauthor_data");
		  hasErrors = checkIsCoauthorDataInvalid(coauthorData);
		  
		  if(hasErrors == true) {
			  execution.setVariable("uspesnaValidacijaKoautora", false);
			  System.out.println("Podaci o koautoru imaju gresku");
		  } else {
			  execution.setVariable("uspesnaValidacijaKoautora", true);
			  System.out.println("Podaci o koautoru nemaju gresku");
		  } 
	}
	
	public boolean checkIsCoauthorDataInvalid(List<FormSubmissionDto> coauthorData) {
		
		for (FormSubmissionDto formField : coauthorData) {
			if(formField.getFieldId().equals("ime")) {
				if(checkCharacters3(formField.getFieldValue()) == false) {
					return true;
				}
			}
			if(formField.getFieldId().equals("prezime")) {
				if(checkCharacters3(formField.getFieldValue()) == false) {
					return true;
				}
			}
			if(formField.getFieldId().equals("grad")) {
				if(checkCharacters5(formField.getFieldValue()) == false) {
					return true;
				}
			}
			if(formField.getFieldId().equals("drzava")) {
				if(checkCharacters5(formField.getFieldValue()) == false) {
					return true;
				}
			}
			if(formField.getFieldId().equals("email")) {
				if(checkMail(formField.getFieldValue()) == false) {
					return true;
				}
			}
		}

		return false; //FALSE oznacava da je forma ispravna
	}
	
		//Dozvoljena prazna mesta i slova
		public boolean checkCharacters5(String data) {
			if(data.isEmpty()) {
				return false;
			}
			for(Character c :data.toCharArray()) {
				if(Character.isWhitespace(c)== false && Character.isLetter(c) == false) {
					return false;
				}
			}
			return true;
		}	
		
		//Dozvoljena slova
		public boolean checkCharacters3(String data) {
			if(data.isEmpty()) {
				return false;
			}
			for(Character c :data.toCharArray()) {
				if(Character.isLetter(c) == false) {
					return false;
				}
			}
			return true;
		}
		
		public boolean checkMail(String mail) {
			if(mail.isEmpty()) {
				return false;
			}
			if(mail.contains(";")) {
				return false;
			}
			
			if(mail.contains(",")) {
				return false;
			}
			for(Character c:mail.toCharArray()) {
				if(Character.isWhitespace(c)) {
					return false;
				
				}
					
			}
			return true;
		}

}
