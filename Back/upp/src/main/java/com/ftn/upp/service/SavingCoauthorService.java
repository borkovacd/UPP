package com.ftn.upp.service;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.upp.model.Article;
import com.ftn.upp.model.Coauthor;
import com.ftn.upp.model.ExtendedFormSubmissionDto;
import com.ftn.upp.model.FormSubmissionDto;
import com.ftn.upp.repository.ArticleRepository;
import com.ftn.upp.repository.CoauthorRepository;

@Service
public class SavingCoauthorService implements JavaDelegate {
	
	@Autowired
	CoauthorRepository coauthorRepository;
	
	@Autowired
	ArticleRepository articleRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {

	      List<FormSubmissionDto> coauthorData = (List<FormSubmissionDto>)execution.getVariable("coauthor_data");
	      List<ExtendedFormSubmissionDto> articleData = (List<ExtendedFormSubmissionDto>)execution.getVariable("article_data");
	     
	      Coauthor coauthor = new Coauthor();
	      
	      for (FormSubmissionDto formField : coauthorData) {
			if(formField.getFieldId().equals("ime")) {
				coauthor.setFirstName(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("prezime")) {
				coauthor.setLastName(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("email")) {
				coauthor.setEmail(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("grad")) {
				coauthor.setCity(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("drzava")) {
				coauthor.setState(formField.getFieldValue());
			}
	      }
	      
	      coauthorRepository.save(coauthor);
	      
	      String articleTitle = "";
		  for (ExtendedFormSubmissionDto formField : articleData) {
			if(formField.getFieldId().equals("naslov")) {
				articleTitle = formField.getFieldValue();
			}
		  }
		  
		  Article article = articleRepository.findOneByTitle(articleTitle);
		  System.out.println("Article of coauthors : " + article.getTitle());
		  article.getCoauthors().add(coauthor);
		  
		  articleRepository.save(article);
		  
		 
		  
		 
	      
	      

	}
}


