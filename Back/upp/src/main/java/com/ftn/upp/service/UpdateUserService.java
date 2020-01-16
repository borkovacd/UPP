package com.ftn.upp.service;

import java.util.List;

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
public class UpdateUserService implements JavaDelegate {

	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
	      
	      List<FormSubmissionDto> registration = (List<FormSubmissionDto>)execution.getVariable("registration");
	      String username = "";
	      for (FormSubmissionDto formField : registration) {
				if(formField.getFieldId().equals("username")) {
					username = formField.getFieldValue();
				}
		      }
	      
	      User korisnik = userRepository.findOneByUsername(username);
	      
	      korisnik.setActivated(true);
	      
	      userRepository.save(korisnik);
	      
	}
}

