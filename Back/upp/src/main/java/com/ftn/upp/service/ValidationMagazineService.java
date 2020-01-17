package com.ftn.upp.service;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.upp.model.FormSubmissionDto;
import com.ftn.upp.repository.MagazineRepository;

@Service
public class ValidationMagazineService implements JavaDelegate {

	
	@Autowired
	MagazineRepository magazineRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		  
		  boolean hasErrors = false;
		  
		  List<FormSubmissionDto> magazine = (List<FormSubmissionDto>)execution.getVariable("magazine");
		  hasErrors = checkIsMagazineFormInvalid(magazine);
		  
		  if(hasErrors == true) {
			  execution.setVariable("uspesnaValidacijaCasopisa", false);
			  System.out.println("IMA GRESAKA");
		  } else {
			  execution.setVariable("uspesnaValidacijaCasopisa", true);
			  System.out.println("NEMA GRESAKA");
		  } 
	}
	
	public boolean checkIsMagazineFormInvalid(List<FormSubmissionDto> magazine) {
		
		for (FormSubmissionDto formField : magazine) {
			if(formField.getFieldId().equals("naziv")) {
				if(checkCharacters1(formField.getFieldValue()) == false) {
					return true;
				}
			}
			if(formField.getFieldId().equals("issn_broj")) {
				if(checkCharacters2(formField.getFieldValue()) == false) {
					return true;
				}
			}
	
		}

		return false; //FALSE oznacava da je forma ispravna
	}
	
	//Dozvoljena PRAZNA MESTA, CIFRE i SLOVA
	public boolean checkCharacters1(String data) {
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
	
	//Dozvoljena samo CIFRE i SLOVA
	public boolean checkCharacters2(String data) {
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
}