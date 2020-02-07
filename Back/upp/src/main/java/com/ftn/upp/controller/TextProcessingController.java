package com.ftn.upp.controller;

import java.util.HashMap;
import java.util.List;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.upp.model.FormFieldsDto;
import com.ftn.upp.model.FormSubmissionDto;
import com.ftn.upp.model.User;
import com.ftn.upp.service.UserService;


@RestController
@RequestMapping("/textProcessing")
public class TextProcessingController {
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private FormService formService;
	
	@Autowired
	private UserService userService;
	
	// Pokretanje procesa obrade podnetog teksta
	@GetMapping(path = "/start", produces = "application/json")
	public @ResponseBody FormFieldsDto startCreateMagazineProcess() {
		
		//User loggedInUser = userService.getCurrentUser();
		//identityService.setAuthenticatedUserId(loggedInUser.getUsername());
		//System.out.println("Logged in Camunda user with username: " + identityService.getCurrentAuthentication().getUserId());
				
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("Proces_ObradeTeksta");
		
		return new FormFieldsDto(null, pi.getId(), null);
	}
	
	// Preuzimanje forme za novi task
	@GetMapping(path = "/getTaskForm/{processInstanceId}", produces = "application/json")
    public @ResponseBody FormFieldsDto getNewTask(@PathVariable String processInstanceId) {
		
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		if(tasks.isEmpty()) {
			return new FormFieldsDto(null, null, null);
		} else {
			Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
			//System.out.println("ASSIGNEE : " + task.getAssignee());
			//System.out.println("TASK ZA KOJI PREUZIMAM FORMU: " + task.getId());
			TaskFormData tfd = formService.getTaskFormData(task.getId());
			List<FormField> properties = tfd.getFormFields();
			/*for(FormField fp : properties) {
				System.out.println(fp.getId() + fp.getType());
			}*/
			return new FormFieldsDto(task.getId(), null, properties);
		}
    }
	
	
	// Odlucivanje o registraciji
	@PostMapping(path = "/decideOnRegistration/{taskId}", produces = "application/json")
    public @ResponseBody boolean decideOnRegistration(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		//System.out.println("Naziv ovog taska je: " + task.getName());
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "registration_decision", dto); 
		formService.submitTaskForm(taskId, map);
		
		boolean neededRegistration = false;
		List<FormSubmissionDto> registrationDecision = (List<FormSubmissionDto>) runtimeService.getVariable(processInstanceId, "registration_decision");
		for (FormSubmissionDto formField : registrationDecision) {
			if(formField.getFieldId().equals("hocu_registraciju")) {
				if(formField.getFieldValue().equals("true")) {
					neededRegistration = true;
				} else if(formField.getFieldValue().equals("false")) {
					neededRegistration = false;
				}
			}
		}
        return neededRegistration;
    }
	
	private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(FormSubmissionDto temp : list){
			map.put(temp.getFieldId(), temp.getFieldValue());
		}
		
		return map;
	}
	
	

}
