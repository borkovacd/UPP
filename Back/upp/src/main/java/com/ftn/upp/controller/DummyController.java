package com.ftn.upp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.upp.model.FormFieldsDto;
import com.ftn.upp.model.FormSubmissionDto;

import com.ftn.upp.model.TaskDto;
import com.ftn.upp.service.UserService;
import com.ftn.upp.service.ValidationService;
import com.ftn.upp.service.ValidationServiceScientificArea;

@RestController
@RequestMapping("/welcome")
public class DummyController {
	
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	@Autowired
	FormService formService;
	@Autowired
	private ValidationService validationService;
	@Autowired
	private ValidationServiceScientificArea validationServiceScientificArea;
	@Autowired
	private UserService userService;
	
	// POKRETANJE PROCESA REGISTRACIJE
	@GetMapping(path = "/getNewProcess", produces = "application/json")
    public @ResponseBody FormFieldsDto getNewProcess() {
		
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("Proces_Registracije");
		//System.out.println("PI.GETID() : "  + pi.getId());
		return new FormFieldsDto(null, pi.getId(), null);
    }
	
	// PREUZIMANJE FORME ZA NOVI TASK
	@GetMapping(path = "/getNewTask/{processInstanceId}", produces = "application/json")
    public @ResponseBody FormFieldsDto getNewTask(@PathVariable String processInstanceId) {
		
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
		//System.out.println("TASK ZA KOJI PREUZIMAM FORMU: " + task.getId());
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}
		
        return new FormFieldsDto(task.getId(), null, properties);
    }
	
	// PREUZIMANJE FORME ZA NOVI TASK
		@GetMapping(path = "/getNewerTask/{processInstanceId}", produces = "application/json")
	    public @ResponseBody FormFieldsDto getNewerTask(@PathVariable String processInstanceId) {
			
			Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
				System.out.println("TASK ZA KOJI PREUZIMAM FORMU: " + task.getId());
			TaskFormData tfd = formService.getTaskFormData(task.getId());
			List<FormField> properties = tfd.getFormFields();
			for(FormField fp : properties) {
				System.out.println(fp.getId() + fp.getType());
			}
			
	        return new FormFieldsDto(task.getId(), null, properties);
	    }


	// REGISTRACIJA USERA
	@PostMapping(path = "/post/{taskId}", produces = "application/json")
    public @ResponseBody boolean post(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
		//System.out.println("TASK ZA REGISTROVANJE: " + task.getId());
		
		System.out.println("Naziv ovog taska je: " + task.getName());
		String processInstanceId = task.getProcessInstanceId();
		// Varijabla "registration"  mi je potrebna za cuvanje registrovanog korisnika
		runtimeService.setVariable(processInstanceId, "registration", dto);
		formService.submitTaskForm(taskId, map);
		
		boolean hasErrors = false;
		List<FormSubmissionDto> registration = (List<FormSubmissionDto>) runtimeService.getVariable(processInstanceId, "registration");
		hasErrors = validationService.checkIsRegistrationFormInvalid(registration);
		
        return hasErrors;
    }
	
	// REGISTRACIJA USERA
		@PostMapping(path = "/postSomething/{taskId}", produces = "application/json")
	    public @ResponseBody ResponseEntity postSomething(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
			HashMap<String, Object> map = this.mapListToDto(dto);
			
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			
			System.out.println("TASK ovaj koji mi treba: " + task.getId());
			
			System.out.println("Naziv ovog taska je: " + task.getName());
			String processInstanceId = task.getProcessInstanceId();
			// Varijabla "registration"  mi je potrebna za cuvanje registrovanog korisnika
			//runtimeService.setVariable(processInstanceId, "registration", dto);
			formService.submitTaskForm(taskId, map);
			
			//boolean hasErrors = false;
			//List<FormSubmissionDto> registration = (List<FormSubmissionDto>) runtimeService.getVariable(processInstanceId, "registration");
			//hasErrors = validationService.checkIsRegistrationFormInvalid(registration);
			
			return new ResponseEntity<>(HttpStatus.OK);
	    }
	
	// POSTAVLJANJE BROJA NAUCNIH OBLASTI
	@PostMapping(path = "/postNumber/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity postNumber(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		//System.out.println("Naziv ovog taska je: " + task.getName());
		formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }

	// POSTAVLJANJE NAZIVA NAUCNE OBLASTI
	@PostMapping(path = "/postScientificAreaName/{taskId}", produces = "application/json")
    public @ResponseBody boolean postScientificAreaName(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		//System.out.println("Naziv ovog taska je: " + task.getName());
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "scientificArea", dto);
		formService.submitTaskForm(taskId, map);
		
		boolean hasErrors = false;
		List<FormSubmissionDto> scientificArea = (List<FormSubmissionDto>) runtimeService.getVariable(processInstanceId, "scientificArea");
		hasErrors = validationServiceScientificArea.checkIsScientificAreaFormInvalid(scientificArea);
		
        return hasErrors;
    }

	
	
	
	
	// PREUZIMANJE LISTE SVIH TASKOVA
	@GetMapping(path = "/get/tasks/{processInstanceId}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<TaskDto>> get(@PathVariable String processInstanceId) {
		
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		List<TaskDto> dtos = new ArrayList<TaskDto>();
		for (Task task : tasks) {
			TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee());
			dtos.add(t);
		}
		
        return new ResponseEntity(dtos,  HttpStatus.OK);
    }
	
	// CLAIM-OVANJE TASKA
	@PostMapping(path = "/tasks/claim/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity claim(@PathVariable String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		//System.out.println("TASK KOJI CLAIMUJEM: " + task.getId());
		String processInstanceId = task.getProcessInstanceId();
		String user = (String) runtimeService.getVariable(processInstanceId, "username");
		taskService.claim(taskId, user);
		System.out.println("Task " + task.getName() + " preuzet je strane korisnika " + user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@GetMapping(path = "/tasks/complete/{processInstanceId}/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<TaskDto>> complete(@PathVariable String processInstanceId, @PathVariable String taskId) {
		
		List<Task> tasksTemp = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		for (Task task : tasksTemp) {
			System.out.println("Task: IME " + task.getName() + " ID " + task.getId());
		}
		
		Task taskTemp = taskService.createTaskQuery().taskId(taskId).singleResult();
		System.out.println("TASK KOJI COMPLETEUJEM " + taskTemp.getId());
		taskService.complete(taskId);
		System.out.println("Task " + taskTemp.getName() + " je izvrsen");
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(taskTemp.getProcessInstanceId()).list();
		List<TaskDto> dtos = new ArrayList<TaskDto>();
		for (Task task : tasks) {
			TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee());
			dtos.add(t);
		}
        return new ResponseEntity<List<TaskDto>>(dtos, HttpStatus.OK);
    }
	
	@RequestMapping(value="/confirmationOfApplication/{processInstanceId}", method = RequestMethod.GET)
	public @ResponseBody boolean aktiviraj(@PathVariable String processInstanceId){
		runtimeService.setVariable(processInstanceId, "confirmationOfApplication", true);
		
		List<FormSubmissionDto> registration = (List<FormSubmissionDto>) runtimeService.getVariable(processInstanceId, "registration");
		
		String username = "";
	    for (FormSubmissionDto formField : registration) {
			if(formField.getFieldId().equals("username")) {
				username = formField.getFieldValue();
				//System.out.println("USERNAME JE : " + username);
			}
	     }
	    
	    userService.saveUser(username);
	    
		return true;
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
