package com.ftn.upp.service;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.upp.model.FormSubmissionDto;
import com.ftn.upp.repository.UserRepository;

@Service
public class SavingUserService implements JavaDelegate{

	@Autowired
	IdentityService identityService;
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {

	      List<FormSubmissionDto> registration = (List<FormSubmissionDto>)execution.getVariable("registration");
	     
	      User user = identityService.newUser("");
	      com.ftn.upp.model.User korisnik = new com.ftn.upp.model.User();
	      for (FormSubmissionDto formField : registration) {
			if(formField.getFieldId().equals("username")) {
				user.setId(formField.getFieldValue());
				korisnik.setUsername(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("password")) {
				user.setPassword(formField.getFieldValue());
				korisnik.setPassword(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("ime")) {
				user.setFirstName(formField.getFieldValue());
				korisnik.setFirstName(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("prezime")) {
				user.setLastName(formField.getFieldValue());
				korisnik.setLastName(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("email")) {
				user.setEmail(formField.getFieldValue());
				korisnik.setEmail(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("grad")) {
				korisnik.setCity(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("drzava")) {
				korisnik.setState(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("titula")) {
				korisnik.setTitle(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("recenzent")) {
				//System.out.println("Recenzent value: " + formField.getFieldValue());
				if(formField.getFieldValue().equalsIgnoreCase("true"))
					korisnik.setReviewer(true);
				else if(formField.getFieldValue().equalsIgnoreCase("false"))
					korisnik.setReviewer(true);
			}
			korisnik.setUserType("obicanKorisnik");
	      }
	      
	      Group group = identityService.newGroup("registrovani_korisnici");
	      identityService.saveGroup(group);
	      System.out.println("Kreirana je nova grupa.");
	      
	      identityService.saveUser(user);
	      identityService.createMembership(user.getId(), group.getId());
	      
	      
	      userRepository.save(korisnik);
	      
	      
	}
}

