package com.ftn.upp.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidationException;
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

import com.ftn.upp.dto.FormSubmissionWithFileDto;
import com.ftn.upp.model.ExtendedFormSubmissionDto;
import com.ftn.upp.model.FormFieldsDto;
import com.ftn.upp.model.FormSubmissionDto;
import com.ftn.upp.model.Magazine;
import com.ftn.upp.model.MagazineScientificArea;
import com.ftn.upp.model.Membership;
import com.ftn.upp.model.TaskDto;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.MagazineScientificAreaRepository;
import com.ftn.upp.repository.ScientificAreaRepository;
import com.ftn.upp.service.MagazineService;
import com.ftn.upp.service.MembershipService;
import com.ftn.upp.service.UserService;
import com.ftn.upp.service.ValidationCoauthorService;

import sun.misc.BASE64Decoder;


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
	
	@Autowired
	private MagazineService magazineService;
	
	@Autowired
	private MembershipService membershipService;
	
	@Autowired
	private MagazineScientificAreaRepository magazineScientificAreaRepository;
	
	@Autowired
	private ValidationCoauthorService validationCoauthorService;
	
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
			TaskFormData tfd = formService.getTaskFormData(task.getId());
			List<FormField> properties = tfd.getFormFields();
			return new FormFieldsDto(task.getId(), null, properties);
		}
    }
	
	@GetMapping(path = "/getTaskFormMagazinesChoosing/{processId}", produces = "application/json")
    public @ResponseBody FormFieldsDto getTaskFormMagazinesChoosing(@PathVariable String processId) {

		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processId).list();
		List<TaskDto> taskDTOList = new ArrayList<TaskDto>();
		
		if(tasks.size()==0){
			System.out.println("Prazna lista, nema vise taskova");
		}
		for(Task T: tasks)
		{
			System.out.println("Dodaje task "+T.getName());
			taskDTOList.add(new TaskDto(T.getId(), T.getName(), T.getAssignee()));
		}
		
		Task nextTask = tasks.get(0);

		
		TaskFormData tfd = formService.getTaskFormData(nextTask.getId());
		List<FormField> properties = tfd.getFormFields();
		
		// dinamicko ucitavanje
		List<Magazine> magazines = magazineService.findAll();
		for (FormField fp: properties)
		{
			if (fp.getId().equals("casopisi"))
			{
				EnumFormType enumType = (EnumFormType) fp.getType();
				enumType.getValues().clear();
				String openAccess ;
				for (Magazine magazine: magazines)
				{
					if (magazine.isOpenAccess() == true)
					{
						openAccess = "open-access" ;
					}
					else
					{
						openAccess = "nije open-access" ;
					}
					enumType.getValues().put(magazine.getId().toString(), magazine.getTitle() + " (" + openAccess + ")");
				}
				break ;
			}
		}
		
        return new FormFieldsDto(nextTask.getId(), processId, properties);
    }
	
	@GetMapping(path = "/getTaskFormArticleInformation/{processId}", produces = "application/json")
    public @ResponseBody FormFieldsDto getTaskFormArticleInformation(@PathVariable String processId) {

		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processId).list();
		List<TaskDto> taskDTOList = new ArrayList<TaskDto>();
		
		if(tasks.size()==0){
			System.out.println("Prazna lista, nema vise taskova");
		}
		for(Task T: tasks)
		{
			System.out.println("Dodaje task "+T.getName());
			taskDTOList.add(new TaskDto(T.getId(), T.getName(), T.getAssignee()));
		}
		
		Task nextTask = tasks.get(0);

		
		TaskFormData tfd = formService.getTaskFormData(nextTask.getId());
		List<FormField> properties = tfd.getFormFields();
		
		// dinamicko ucitavanje
		List<MagazineScientificArea> scientificAreas = magazineScientificAreaRepository.findAll();
		for (FormField fp: properties)
		{
			if (fp.getId().equals("naucne_oblasti"))
			{
				EnumFormType enumType = (EnumFormType) fp.getType();
				enumType.getValues().clear();
				
				for (MagazineScientificArea sa: scientificAreas)
				{
					enumType.getValues().put(sa.getId().toString(), sa.getName());
				}
				break ;
			}
		}
		
        return new FormFieldsDto(nextTask.getId(), processId, properties);
    }
	
	@GetMapping(path = "/getTaskFormChoosingReviewers/{taskId}", produces = "application/json")
    public @ResponseBody FormFieldsDto getTaskFormChoosingReviewers(@PathVariable String taskId) {
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();


		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		
		// dinamicko ucitavanje
		List<User> reviewers = userService.findAllReviewers(taskId);
		for (FormField fp: properties)
		{
			if (fp.getId().equals("izborRecenzenata"))
			{
				EnumFormType enumType = (EnumFormType) fp.getType();
				enumType.getValues().clear();
				
				for (User reviewer: reviewers)
				{
					enumType.getValues().put(reviewer.getId().toString(), reviewer.getFirstName() + " " + reviewer.getLastName());
				}
				break ;
			}
		}
		
        return new FormFieldsDto(task.getId(), processInstanceId, properties);
    }
	
	@GetMapping(path = "/getTaskFormChoosingReviewersFiltered/{taskId}", produces = "application/json")
    public @ResponseBody FormFieldsDto getTaskFormChoosingReviewersFiltered(@PathVariable String taskId) {
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();


		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		
		// dinamicko ucitavanje
		List<User> reviewers = userService.findAllReviewersFiltered(taskId);
		for (FormField fp: properties)
		{
			if (fp.getId().equals("izborRecenzenata"))
			{
				EnumFormType enumType = (EnumFormType) fp.getType();
				enumType.getValues().clear();
				
				for (User reviewer: reviewers)
				{
					enumType.getValues().put(reviewer.getId().toString(), reviewer.getFirstName() + " " + reviewer.getLastName());
				}
				break ;
			}
		}
		
        return new FormFieldsDto(task.getId(), processInstanceId, properties);
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
	
	@PostMapping(path = "/confirmLoggingIn/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity confirmLoggingIn(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		
		User author = userService.getCurrentUser();
		runtimeService.setVariable(processInstanceId, "author", author.getUsername());
		formService.submitTaskForm(taskId, map);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(path = "/chooseMagazine/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<Boolean> chooseMagazine(@RequestBody List<ExtendedFormSubmissionDto> formData, @PathVariable String taskId) {
		
		HashMap<String, Object> map = this.mapListToDto2(formData);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		
		for(ExtendedFormSubmissionDto item: formData){
			String fieldId = item.getFieldId();
			
			if(fieldId.equals("casopisi")){
				if(item.getCategories().size()!=1){
					System.out.println("Potrebno je izabrati tacno 1 casopis!");	
					return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
				}
			}
		}

		runtimeService.setVariable(processInstanceId, "chosenMagazine", formData);
		
		Magazine chosenMagazine = null;
		boolean openAccess = false;
		List<ExtendedFormSubmissionDto> chosenMagazineData = (List<ExtendedFormSubmissionDto>) runtimeService.getVariable(processInstanceId, "chosenMagazine");
		for(ExtendedFormSubmissionDto item: chosenMagazineData) {
			 String fieldId=item.getFieldId();
			 if(fieldId.equals("casopisi")){
				  List<Magazine> allMagazines = magazineService.findAll();
				  for(Magazine m : allMagazines){
					  for(String selected: item.getCategories()){
						  String idM = m.getId().toString();
						  if(idM.equals(selected)){
							  System.out.println(m.getTitle());
							  chosenMagazine = m;
							  openAccess = m.isOpenAccess();
							  runtimeService.setVariable(processInstanceId, "openAccess", openAccess);
							  runtimeService.setVariable(processInstanceId, "glavniUrednik", m.getMainEditor().getUsername());
							  
						  }
					  }
				  }
			 }
		}
		
		boolean paymentRequested = true;
		
		if(openAccess == false) {
			paymentRequested = false;
			formService.submitTaskForm(taskId, map);
			return new ResponseEntity<Boolean>(paymentRequested, HttpStatus.OK);
		}
		
		String authorUsername = (String) runtimeService.getVariable(processInstanceId, "author");
		User author = userService.findUserByUsername(authorUsername);
		System.out.println("Autor je: " + author.getUsername() + "(" + author.getFirstName() + " " + author.getLastName() + ")");
		
		List<Membership> allMemberships = membershipService.findAll();
		for(Membership membership : allMemberships) {
			if(membership.getMagazine().getId() == chosenMagazine.getId()) {
				if(membership.getUser().getId() == author.getId()) {
					System.out.println("Autor ima aktivnu clanarinu u izabranom casopisu");
					paymentRequested = false;
				}
			}
		}
		
		formService.submitTaskForm(taskId, map);
		return new ResponseEntity<Boolean>(paymentRequested, HttpStatus.OK);
		
    }
	
	@PostMapping(path = "/articleData/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity postArticleData(@RequestBody FormSubmissionWithFileDto dto, @PathVariable String taskId) throws IOException {
		HashMap<String, Object> map = this.mapListToDto2(dto.getForm());
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		
		for(ExtendedFormSubmissionDto item: dto.getForm()){
			String fieldId = item.getFieldId();
			
			if(fieldId.equals("naucne_oblasti")){
				if(item.getCategories().size()!=1){
					System.out.println("Potrebno je izabrati tacno 1 casopis!");	
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}
		}
		
		runtimeService.setVariable(processInstanceId, "article_data", dto.getForm()); 
		runtimeService.setVariable(processInstanceId, "file_name", dto.getFileName()); 
		formService.submitTaskForm(taskId, map);
		
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] decodedBytes = decoder.decodeBuffer(dto.getFile());
		
		runtimeService.setVariable(processInstanceId, "file_decoded", decodedBytes); 

		File file = new File("pdf_files/" + dto.getFileName());
		FileOutputStream fop = new FileOutputStream(file);

		fop.write(decodedBytes);
		fop.flush();
		fop.close();
		
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
	
	@PostMapping(path = "/updateArticleData/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<Boolean> updateArticleData(@RequestBody FormSubmissionWithFileDto dto, @PathVariable String taskId) throws IOException {
		HashMap<String, Object> map = this.mapListToDto2(dto.getForm());
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if(task == null) {
			return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
		}
		String processInstanceId = task.getProcessInstanceId();
		 
		runtimeService.setVariable(processInstanceId, "file_name", dto.getFileName()); 
		formService.submitTaskForm(taskId, map);
		
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] decodedBytes = decoder.decodeBuffer(dto.getFile());
		
		runtimeService.setVariable(processInstanceId, "file_decoded", decodedBytes); 

		File file = new File("pdf_files/" + dto.getFileName());
		FileOutputStream fop = new FileOutputStream(file);

		fop.write(decodedBytes);
		fop.flush();
		fop.close();
		
        return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
    }
	
	@PostMapping(path = "/coauthorData/{taskId}", produces = "application/json")
    public @ResponseBody boolean postCoauthorData(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "coauthor_data", dto);
		formService.submitTaskForm(taskId, map);
		
		boolean hasErrors = false;
		List<FormSubmissionDto> coauthorData = (List<FormSubmissionDto>) runtimeService.getVariable(processInstanceId, "coauthor_data");
		hasErrors = validationCoauthorService.checkIsCoauthorDataInvalid(coauthorData);
		
        return hasErrors;
    }
	
	@SuppressWarnings("deprecation")
	@GetMapping(path = "/loadTask/{taskId}", produces = "application/json")
    public @ResponseBody FormFieldsDto loadNextTask(@PathVariable String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		TaskFormData tfd = formService.getTaskFormData(taskId);
		List<FormField> properties = tfd.getFormFields();
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
			
			/*if(fp.getDefaultValue() != null ) {
				System.out.println("NIJE NULL DEFAULT");
				System.out.println(fp.getDefaultValue().toString());
			} else {
				System.out.println(" NULL DEFAULT");
			}*/
			
		}
		
        return new FormFieldsDto(taskId, task.getProcessInstanceId(), properties);
    }
	
	@PostMapping(path = "/articleReviewDecision/{taskId}", produces = "application/json")
    public @ResponseBody boolean articleReviewDecision(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "article_review_decision", dto); 
		
		
		boolean articleRejected = false;
		List<FormSubmissionDto> articleReviewDecision = (List<FormSubmissionDto>) runtimeService.getVariable(processInstanceId, "article_review_decision");
		for (FormSubmissionDto formField : articleReviewDecision) {
			if(formField.getFieldId().equals("prikladan")) {
				if(formField.getFieldValue().equals("true")) {
					articleRejected = false;
				} else if(formField.getFieldValue().equals("false")) {
					articleRejected = true;
				}
			}
		}
		
		formService.submitTaskForm(taskId, map);
		
        return articleRejected;
    }
	
	@PostMapping(path = "/decideOnPDF/{taskId}", produces = "application/json")
    public @ResponseBody boolean decideOnPDF(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "pdf_decision", dto); 
		
		boolean correctionNeeded = false;
		List<FormSubmissionDto> pdfDecision = (List<FormSubmissionDto>) runtimeService.getVariable(processInstanceId, "pdf_decision");
		for (FormSubmissionDto formField : pdfDecision) {
			if(formField.getFieldId().equals("dobro_formatiran")) {
				if(formField.getFieldValue().equals("false") || formField.getFieldValue() == null) {
					if(formField.getFieldValue() == null) {
						System.out.println("Poslato null ali gde treba je ");
					}
					correctionNeeded = true;
				} else if(formField.getFieldValue().equals("true") ) {
					
					correctionNeeded = false;
				}
			}
		}
		
		formService.submitTaskForm(taskId, map);
        return correctionNeeded;
    }
	
	@PostMapping(path = "/chooseReviewers/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<Boolean> chooseReviewers(@RequestBody List<ExtendedFormSubmissionDto> formData, @PathVariable String taskId) {
		
		HashMap<String, Object> map = this.mapListToDto2(formData);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		
		for(ExtendedFormSubmissionDto item: formData){
			String fieldId = item.getFieldId();
			
			if(fieldId.equals("izborRecenzenata")){
				if(item.getCategories().size() < 2){
					System.out.println("Potrebno je izabrati barem 2 recenzenta!");	
					return new ResponseEntity<Boolean>(false, HttpStatus.OK);
				}
			}
		}

		runtimeService.setVariable(processInstanceId, "chosenReviewers", formData);
		
		List<ExtendedFormSubmissionDto> chosenReviewersData = (List<ExtendedFormSubmissionDto>) runtimeService.getVariable(processInstanceId, "chosenReviewers");
		for(ExtendedFormSubmissionDto item: chosenReviewersData) {
			 String fieldId=item.getFieldId();
			 if(fieldId.equals("izborRecenzenata")){
				  List<User> allUsers = userService.getAll();
				  for(User user : allUsers){
					  for(String selected: item.getCategories()){
						  String idReviewer = user.getId().toString();
						  if(idReviewer.equals(selected)){
							  System.out.println("Izabran je recenzent: " + user.getUsername());  
						  }
					  }
				  }
			 }
		}
		
		formService.submitTaskForm(taskId, map);
		
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		
    }
	
	@PostMapping(path = "/payMembership/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity payMembership(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		//System.out.println("Naziv ovog taska je: " + task.getName());
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "payment", dto); 
		formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	
	private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(FormSubmissionDto temp : list){
			map.put(temp.getFieldId(), temp.getFieldValue());
		}
		
		return map;
	}
	
	private HashMap<String, Object> mapListToDto2(List<ExtendedFormSubmissionDto> list)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(ExtendedFormSubmissionDto temp : list){
			map.put(temp.getFieldId(), temp.getFieldValue());
		}
		
		return map;
	}
	
	

}
