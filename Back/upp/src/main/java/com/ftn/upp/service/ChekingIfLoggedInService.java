package com.ftn.upp.service;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.upp.model.User;

@Service
public class ChekingIfLoggedInService implements JavaDelegate {
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	UserService userService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		boolean loggedIn = false;
		
		User loggedInUser = userService.getCurrentUser();
		if(loggedInUser != null) {
			loggedIn = true;
			runtimeService.setVariable(execution.getProcessInstanceId(), "author", loggedInUser.getUsername());
		} else {
			loggedIn = false;
		}
		
		runtimeService.setVariable(execution.getProcessInstanceId(), "loggedIn", loggedIn);
  
	}

}
