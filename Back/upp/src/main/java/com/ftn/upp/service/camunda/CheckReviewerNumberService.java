package com.ftn.upp.service.camunda;

import java.util.ArrayList;
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
import com.ftn.upp.model.User;
import com.ftn.upp.repository.ArticleRepository;
import com.ftn.upp.service.MagazineService;
import com.ftn.upp.service.UserService;


/**
 * Sistem treba automatski da proveri da li postoje minimum 2 recenzenta za izabranu naucnu oblast (koji rade za casopis)
 * @author Borkovac
 *
 */
@Service
public class CheckReviewerNumberService implements JavaDelegate {
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	MagazineService magazineService;
	
	@Autowired
	ArticleRepository articleRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("*****************************************");
		System.out.println("Provera da li postoji odgovarajuci broj recenzenata");
		
		boolean enoughReviewersExist = false;
		int numberOfReviewers = 0;
		
		List<ExtendedFormSubmissionDto> articleData = (List<ExtendedFormSubmissionDto>) execution.getVariable("article_data");
		String articleTitle = "";
		for (ExtendedFormSubmissionDto formField : articleData) {
			if(formField.getFieldId().equals("naslov")) {
				articleTitle = formField.getFieldValue();
			}
		}
		Article article = articleRepository.findOneByTitle(articleTitle);
		Set<MagazineScientificArea> scientificAreas =  article.getArticleAreas();
		
		//Izabrana naucna oblast
		MagazineScientificArea scientificArea = scientificAreas.iterator().next();
		System.out.println("Izabrana naucna oblast: " + scientificArea.getName());
		
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
		
		List<User> allUsers = userService.getAll();
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
		
		for(User mr : magazineReviewers) {
			for(MagazineScientificArea sa : mr.getInterestedAreas()) {
				if(sa.getId() == scientificArea.getId()) {
					System.out.println("Za naucnu oblast (" + scientificArea.getName() + ") pronadjen je recenzent (" + mr.getUsername() + ")");
					numberOfReviewers = numberOfReviewers + 1;
				}
			}
		}
		
		System.out.println("Postoji ukupno " + numberOfReviewers + " recenzenata za izabranu naucnu oblast");
		if(numberOfReviewers >= 2) {
			enoughReviewersExist = true;
		} else {
			enoughReviewersExist = false;
		}
		
		runtimeService.setVariable(execution.getProcessInstanceId(), "postojeRecenzenti", enoughReviewersExist);
  
	}


}
