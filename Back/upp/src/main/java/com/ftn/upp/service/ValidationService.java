package com.ftn.upp.service;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.upp.model.FormSubmissionDto;
import com.ftn.upp.repository.UserRepository;

@Service
public class ValidationService implements JavaDelegate {

	@Autowired
	IdentityService identityService;
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		  
		  boolean hasErrors = false;
		  
		  List<FormSubmissionDto> registration = (List<FormSubmissionDto>)execution.getVariable("registration");
		  hasErrors = checkIsRegistrationFormInvalid(registration);
		  
		  if(hasErrors == true) {
			  execution.setVariable("uspesnaValidacija", false);
			  System.out.println("IMA GRESAKA");
		  } else {
			  execution.setVariable("uspesnaValidacija", true);
			  System.out.println("NEMA GRESAKA");
		  } 
	}

	public boolean checkIsRegistrationFormInvalid(List<FormSubmissionDto> registration) {
		
		for (FormSubmissionDto formField : registration) {
			if(formField.getFieldId().equals("username")) {
				if(checkCharacters1(formField.getFieldValue()) == false) {
					return true;
				}
			}
			if(formField.getFieldId().equals("password")) {
				if(checkCharacters1(formField.getFieldValue()) == false) {
					return true;
				}
			}
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
			if(formField.getFieldId().equals("titula")) {
				if(checkCharacters4(formField.getFieldValue()) == false) {
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
	
	//Dozvoljeno prazno ili samo slova
	public boolean checkCharacters4(String data) {
		if(data.isEmpty()) {
			return true;
		}
		for(Character c :data.toCharArray()) {
			if(Character.isLetter(c) == false) {
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
	
	//Dozvoljena samo cifre i slova
	public boolean checkCharacters1(String data) {
		if(data.isEmpty()) {
			return false;
		}
		for(Character c :data.toCharArray()) {
			if(Character.isLetterOrDigit(c) == false) {
				return false;
			}
		}
		return true;
	}
	
	//Dozvoljena prazna mesta,cifre i slova
	public boolean checkCharacters2(String data) {
		if(data.isEmpty()) {
			return false;
		}
		for(Character c :data.toCharArray()) {
			if(Character.isWhitespace(c)== false && Character.isLetterOrDigit(c) == false) {
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

