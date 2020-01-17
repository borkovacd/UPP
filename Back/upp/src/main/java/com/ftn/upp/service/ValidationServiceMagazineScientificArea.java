package com.ftn.upp.service;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import com.ftn.upp.model.FormSubmissionDto;

@Service
public class ValidationServiceMagazineScientificArea implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		  
		  boolean hasErrors = false;
		  
		  List<FormSubmissionDto> magazineScientificArea = (List<FormSubmissionDto>)execution.getVariable("magazineScientificArea");
		  hasErrors = checkIsScientificAreaFormInvalid(magazineScientificArea);
		  
		  if(hasErrors == true) {
			  execution.setVariable("uspesnaValidacijaCasopisa2", false);
			  System.out.println("IMA GRESAKA (Naucna oblast MAGAZINA)");
		  } else {
			  execution.setVariable("uspesnaValidacijaCasopisa2", true);
			  System.out.println("NEMA GRESAKA (Naucna oblast MAGAZINA)");
		  } 
	}
	
	public boolean checkIsScientificAreaFormInvalid (List<FormSubmissionDto> magazineScientificArea) {
		
		for (FormSubmissionDto formField : magazineScientificArea) {
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

