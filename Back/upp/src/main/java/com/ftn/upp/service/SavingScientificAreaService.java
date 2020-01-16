package com.ftn.upp.service;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.upp.model.FormSubmissionDto;
import com.ftn.upp.model.ScientificArea;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.ScientificAreaRepository;
import com.ftn.upp.repository.UserRepository;

@Service
public class SavingScientificAreaService implements JavaDelegate {

	
	@Autowired
	ScientificAreaRepository scientificAreaRepository;
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		  
	      List<FormSubmissionDto> scientificArea = (List<FormSubmissionDto>)execution.getVariable("scientificArea");
	      ScientificArea sa = new ScientificArea();
	      for (FormSubmissionDto formField : scientificArea) {
			if(formField.getFieldId().equals("naucna_oblast")) {
				sa.setName(formField.getFieldValue());
			}
	      }
	      
	      List<FormSubmissionDto> registration = (List<FormSubmissionDto>)execution.getVariable("registration");
	      String username = "";
	      for (FormSubmissionDto formField : registration) {
				if(formField.getFieldId().equals("username")) {
					username = formField.getFieldValue();
				}
		      }
	      
	      User korisnik = userRepository.findOneByUsername(username);
	      
	      sa.setUser(korisnik);
	      
	      scientificAreaRepository.save(sa);
	      
	}
}

