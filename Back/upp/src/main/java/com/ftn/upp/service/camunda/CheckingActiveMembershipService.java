package com.ftn.upp.service.camunda;

import java.util.List;
import java.util.Set;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.upp.model.Article;
import com.ftn.upp.model.ExtendedFormSubmissionDto;
import com.ftn.upp.model.Magazine;
import com.ftn.upp.model.MagazineScientificArea;
import com.ftn.upp.model.Membership;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.ArticleRepository;
import com.ftn.upp.service.MagazineService;
import com.ftn.upp.service.MembershipService;
import com.ftn.upp.service.UserService;

@Service
public class CheckingActiveMembershipService implements JavaDelegate {
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	MagazineService magazineService;
	
	@Autowired
	MembershipService membersipService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("*****************************************");
		System.out.println("Provera da li autor ima aktivnu clanarinu");
		
		boolean activeMembershipExists = false;
		
		String authorUsername = (String) runtimeService.getVariable(execution.getProcessInstanceId(), "author");
		User author = userService.findUserByUsername(authorUsername);
		//Autor
		System.out.println("Autor je: " + author.getUsername() + "(" + author.getFirstName() + " " + author.getLastName() + ")");
		
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
		System.out.println("Izabrani casopis: " + magazine.getTitle());
		
		List<Membership> allMemberships = membersipService.findAll();
		for(Membership membership : allMemberships) {
			if(membership.getMagazine().getId() == magazine.getId()) {
				if(membership.getUser().getId() == author.getId()) {
					System.out.println("Autor ima aktivnu clanarinu u izabranom casopisu");
					activeMembershipExists = true;
				}
			}
		}
		
		if(activeMembershipExists == false) {
			System.out.println("Autor nema aktivnu clanarinu u izabranom casopisu!");
		}
	
		System.out.println("*****************************************");
		
		runtimeService.setVariable(execution.getProcessInstanceId(), "postojiAktivnaClanarina", activeMembershipExists);
  
	}


}

