package com.ftn.upp.service;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.upp.model.FormSubmissionDto;
import com.ftn.upp.model.ScientificArea;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.ScientificAreaRepository;
import com.ftn.upp.repository.UserRepository;

@Service
public class UserActivationService implements JavaDelegate {

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
	      
	      List<FormSubmissionDto> registration = (List<FormSubmissionDto>) execution.getVariable("registration");
	      String username = "";
	      for (FormSubmissionDto formField : registration) {
				if(formField.getFieldId().equals("username")) {
					username = formField.getFieldValue();
					//System.out.println("USERNAME JE : " + username);
				}
	      }
	      
	  
	      execution.setVariable("activated", true);
	      //userService.saveUser(username);
	      /*boolean confirmed = (boolean) execution.getVariable("confirmationOfApplication");
	      if(confirmed == true) {
	    	  korisnik.setActivated(true);
	    	  userRepository.save(korisnik);
	      } else {
	    	  System.out.println("NIJE CONFIRMED");
	      }*/
	      
	}
}