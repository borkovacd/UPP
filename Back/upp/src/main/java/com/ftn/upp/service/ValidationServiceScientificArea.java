package com.ftn.upp.service;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import com.ftn.upp.model.FormSubmissionDto;

@Service
public class ValidationServiceScientificArea implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		  
		  boolean hasErrors = false;
		  
		  List<FormSubmissionDto> scientificArea = (List<FormSubmissionDto>)execution.getVariable("scientificArea");
		  hasErrors = checkIsScientificAreaFormInvalid(scientificArea);
		  
		  if(hasErrors == true) {
			  execution.setVariable("uspesnaValidacija2", false);
			  System.out.println("IMA GRESAKA (Naucna oblast)");
		  } else {
			  execution.setVariable("uspesnaValidacija2", true);
			  System.out.println("NEMA GRESAKA (Naucna oblast)");
		  } 
	}
	
	public boolean checkIsScientificAreaFormInvalid (List<FormSubmissionDto> scientificArea) {
		
		for (FormSubmissionDto formField : scientificArea) {
			if(formField.getFieldId().equals("naucna_oblast")) {
				if(checkCharacters(formField.getFieldValue()) == false) {
					return true;
				}
			}
		}

		return false; //FALSE oznacava da je forma ispravna
	}

	//Dozvoljena prazna mesta,cifre i slova
	public boolean checkCharacters(String data) {
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

}
