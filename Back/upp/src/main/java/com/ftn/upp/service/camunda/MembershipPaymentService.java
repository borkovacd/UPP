package com.ftn.upp.service.camunda;

import java.util.List;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.upp.model.ExtendedFormSubmissionDto;
import com.ftn.upp.model.Magazine;
import com.ftn.upp.model.Membership;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.MembershipRepository;
import com.ftn.upp.service.MagazineService;
import com.ftn.upp.service.MembershipService;
import com.ftn.upp.service.UserService;

@Service
public class MembershipPaymentService implements JavaDelegate {
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	MagazineService magazineService;
	
	@Autowired
	MembershipService membershipService;
	
	@Autowired
	MembershipRepository membershipRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String authorUsername = (String) runtimeService.getVariable(execution.getProcessInstanceId(), "author");
		User author = userService.findUserByUsername(authorUsername);
		//Autor
		//System.out.println("Autor je: " + author.getUsername() + "(" + author.getFirstName() + " " + author.getLastName() + ")");
		
		Magazine magazine = null;
		List<ExtendedFormSubmissionDto> chosenMagazineData = (List<ExtendedFormSubmissionDto>) runtimeService.getVariable(execution.getProcessInstanceId(), "chosenMagazine");
		for(ExtendedFormSubmissionDto item: chosenMagazineData) {
			 String fieldId=item.getFieldId();
			 if(fieldId.equals("casopisi")){
				  List<Magazine> allMagazines = magazineService.findAll();
				  for(Magazine m : allMagazines){
					  for(String selected: item.getCategories()){
						  String idM = m.getId().toString();
						  if(idM.equals(selected)){
							  magazine = m;
						  }
					  }
				  }
			 }
		}
		//Izabrani casopis
		//System.out.println("Izabrani casopis: " + magazine.getTitle());
		
		Membership membership = new Membership();
		membership.setUser(author);
		membership.setMagazine(magazine);
		
		membershipRepository.save(membership);
		System.out.println("Uplata članarine je uspešno obrađena!");
	}


}

