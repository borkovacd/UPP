package com.ftn.upp.service.camunda;


import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.upp.repository.ArticleRepository;
import com.ftn.upp.service.UserService;

@Service
public class SettingAssignedEditorService implements JavaDelegate {
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ArticleRepository articleRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String assignedEditorUsername = "";
		boolean activeEditorExists = (boolean) runtimeService.getVariable(execution.getProcessInstanceId(), "postojiAktivanEditor");
		System.out.println("Postoji aktivan urednik -> " + activeEditorExists);
		
		if(activeEditorExists == true) {
			assignedEditorUsername = (String) runtimeService.getVariable(execution.getProcessInstanceId(), "scientificAreaEditor");
		} else if (activeEditorExists == false) {
			assignedEditorUsername = (String) runtimeService.getVariable(execution.getProcessInstanceId(), "glavniUrednik");
		}
	
		runtimeService.setVariable(execution.getProcessInstanceId(), "dodeljeniUrednik", assignedEditorUsername);
  
	}


}
