package com.ftn.upp.controller;

import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.ftn.upp.repository.UserRepository;
import com.ftn.upp.service.UserService;
import com.ftn.upp.service.ValidationMagazineService;
import com.ftn.upp.service.ValidationServiceMagazineScientificArea;
import com.ftn.upp.service.ValidationServiceScientificArea;

@RestController
@RequestMapping("/magazine")
public class MagazineController {
	
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	@Autowired
	FormService formService;
	@Autowired
	private ValidationMagazineService validationService;
	@Autowired
	private ValidationServiceMagazineScientificArea validationServiceScientificArea;
	@Autowired
	private UserService userService;
		
	// POKRETANJE PROCESA KREIRANJA CASOPISA
	@GetMapping(path = "/startCreateMagazineProcess/{username}", produces = "application/json")
	public @ResponseBody FormFieldsDto startCreateMagazineProcess(@PathVariable String username) {
			
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("Proces_KreiranjaCasopisa");
		
		System.out.println("POKRETAC USERNAME JE : " + username);
		runtimeService.setVariable(pi.getProcessInstanceId(), "pokretacUsername", username);
		
		
		return new FormFieldsDto(null, pi.getId(), null);
	}
	
	
	// KREIRANJE CASOPISA
	@PostMapping(path = "/create/{taskId}", produces = "application/json")
    public @ResponseBody boolean post(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
		System.out.println("Naziv ovog taska je: " + task.getName());
		String processInstanceId = task.getProcessInstanceId();
		
		runtimeService.setVariable(processInstanceId, "magazine", dto);
		formService.submitTaskForm(taskId, map);
		
		boolean hasErrors = false;
		List<FormSubmissionDto> magazine = (List<FormSubmissionDto>) runtimeService.getVariable(processInstanceId, "magazine");
		hasErrors = validationService.checkIsMagazineFormInvalid(magazine);
		
        return hasErrors;
    }
	
	// POSTAVALJANJE ZELJENOG BROJA NAUCNIH OBLASTI CASOPISA
	@PostMapping(path = "/postMSAnumber/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity postMSAnumber(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	// POSTAVLJANJE NAZIVA NAUCNE OBLASTI CASOPISA
	@PostMapping(path = "/postMSAname/{taskId}", produces = "application/json")
    public @ResponseBody boolean postMSAname(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "magazineScientificArea", dto);
		formService.submitTaskForm(taskId, map);
		
		boolean hasErrors = false;
		List<FormSubmissionDto> scientificArea = (List<FormSubmissionDto>) runtimeService.getVariable(processInstanceId, "magazineScientificArea");
		hasErrors = validationServiceScientificArea.checkIsScientificAreaFormInvalid(scientificArea);
		
        return hasErrors;
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
