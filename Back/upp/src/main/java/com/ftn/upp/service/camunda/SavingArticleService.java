package com.ftn.upp.service.camunda;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.upp.model.Article;
import com.ftn.upp.model.Coauthor;
import com.ftn.upp.model.ExtendedFormSubmissionDto;
import com.ftn.upp.model.FormSubmissionDto;
import com.ftn.upp.model.MagazineScientificArea;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.ArticleRepository;
import com.ftn.upp.repository.CoauthorRepository;
import com.ftn.upp.service.ScientificAreaService;
import com.ftn.upp.service.UserService;

@Service
public class SavingArticleService  implements JavaDelegate {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private ScientificAreaService scientificAreaService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {

	      List<ExtendedFormSubmissionDto> articleData = (List<ExtendedFormSubmissionDto>)execution.getVariable("article_data");
	     
	      Article article = new Article();
	      
	      for (ExtendedFormSubmissionDto formField : articleData) {
			if(formField.getFieldId().equals("naslov")) {
				article.setTitle(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("kljucni_pojmovi")) {
				article.setKeyTerms(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("apstrakt")) {
				article.setArticleAbstract(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("naucne_oblasti")){
				  List<MagazineScientificArea> allScientificAreas = scientificAreaService.findAll();
				  for(MagazineScientificArea sa : allScientificAreas){
					  for(String selectedS: formField.getCategories()){
						  String idS = sa.getId().toString();
						  if(idS.equals(selectedS)){
							  System.out.println(sa.getName());
							  article.getArticleAreas().add(sa);
						  }
					  }
				  }
			 }
	      }
	      
	      
	      User author = userService.getCurrentUser();
	      article.setAuthor(author);
	      
	      articleRepository.save(article);
	}
}


