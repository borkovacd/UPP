package com.ftn.upp.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ftn.upp.dto.UserDTO;
import com.ftn.upp.model.ExtendedFormSubmissionDto;
import com.ftn.upp.model.Magazine;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	MagazineService magazineService;

	public void saveUser(String username) {
		User user = userRepository.findOneByUsername(username);
		user.setActivated(true);
		userRepository.save(user);
		
	}

	public User findUserByUsername(String username) {
		return userRepository.findOneByUsername(username);
	}

	public void activateUser(String username) {
		User user = userRepository.findOneByUsername(username);
		user.setActivated(true);
	}
	
	public List<User> getAll() {
		return userRepository.findAll();
	}
	
	public User getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User u = userRepository.findOneByUsername(principal.toString());
		return u;
	}

	public List<User> findAllReviewers(String taskId) {
		
		System.out.println("Usao u metodu za preuzimanje svih recenzenata izabaranog casopisa");
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		
		Magazine magazine = null;
		
		List<ExtendedFormSubmissionDto> chosenMagazineData = (List<ExtendedFormSubmissionDto>) runtimeService.getVariable(processInstanceId, "chosenMagazine");
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
		
		List<User> allUsers = getAll();
		List<User> magazineReviewers = new ArrayList<User>();
		
		for(User u : allUsers) {
			if(u.getUserType().equals("recenzent")) {
				for(User magazineReviewer: magazine.getReviewerMagazine()) {
					if(magazineReviewer.getId() == u.getId()) {
						System.out.println("Korisnik (" + u.getUsername() + ") je recenzent izabranog casopisa");
						magazineReviewers.add(u);
					}
				}
			}
		}
		
	
		return magazineReviewers;
	}

	
	

}
