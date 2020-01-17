package com.ftn.upp.service;

import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.upp.model.ExtendedFormSubmissionDto;
import com.ftn.upp.model.FormSubmissionDto;
import com.ftn.upp.model.Magazine;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.MagazineRepository;

@Service
public class SavingUpdatedMagazineDataService implements JavaDelegate {
	
	@Autowired
	IdentityService identityService;
	@Autowired
	UserService userService;
	@Autowired
	TaskService taskService;
	@Autowired
	FormService formService;
	@Autowired
	MagazineRepository magazineRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		 System.out.println("Saving Update Service");
		 List<FormSubmissionDto> m = (List<FormSubmissionDto>)execution.getVariable("magazine");
		 List<ExtendedFormSubmissionDto> updateInfo = (List<ExtendedFormSubmissionDto>)execution.getVariable("updateData");
		 
		 String magazineISSN = "";
	     for (FormSubmissionDto formField : m) {
			if(formField.getFieldId().equals("issn_broj")) {
				magazineISSN = formField.getFieldValue();
			}
		 }
		 
		 Magazine magazine = new Magazine();
		 magazine = magazineRepository.findOneByIssn(magazineISSN);
		 for(ExtendedFormSubmissionDto item: updateInfo){
			  String fieldId=item.getFieldId();
			  
			 if(fieldId.equals("uredniciMagazina")){
				  System.out.println("Editori su");
				  System.out.println(item.getCategories().size());
				  List<User> allUsers = userService.getAll();
				  for(User u : allUsers){
					  for(String selectedEd:item.getCategories()){
	
						  System.out.println("Editor sa id je "+selectedEd);
						  String idS=u.getId().toString();
						  if(idS.equals(selectedEd)){
							  System.out.println("Pronadjen editor");
							  System.out.println(u.getFirstName());
							  magazine.getEditorMagazine().add(u);
							  
						  }
					  }
				  }
			 }
			 if(fieldId.equals("recenzentiMagazina")){
				  System.out.println("Recenzenti su su");
				  System.out.println(item.getCategories().size());
				  List<User> allUsers = userService.getAll();
				  for(User u : allUsers){
					  for(String selectedEd:item.getCategories()){
	
						  System.out.println("Recenzent sa id je "+selectedEd);
						  String idS=u.getId().toString();
						  if(idS.equals(selectedEd)){
							  System.out.println("Pronadjen recenzent");
							  System.out.println(u.getFirstName());
							  magazine.getReviewerMagazine().add(u);
							  
						  }
					  }
				  }
			 }
			}
		 
		 magazineRepository.save(magazine);
	}
}
