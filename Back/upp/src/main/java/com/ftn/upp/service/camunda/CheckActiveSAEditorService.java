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
import com.ftn.upp.model.MagazineScientificArea;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.ArticleRepository;
import com.ftn.upp.service.UserService;

@Service
public class CheckActiveSAEditorService implements JavaDelegate {
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ArticleRepository articleRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		boolean activeEditorExist = false;
		
		List<ExtendedFormSubmissionDto> articleData = (List<ExtendedFormSubmissionDto>) execution.getVariable("article_data");
		String articleTitle = "";
		for (ExtendedFormSubmissionDto formField : articleData) {
			if(formField.getFieldId().equals("naslov")) {
				articleTitle = formField.getFieldValue();
			}
		}
		Article article = articleRepository.findOneByTitle(articleTitle);
		Set<MagazineScientificArea> scientificAreas =  article.getArticleAreas();
		MagazineScientificArea scientificArea = scientificAreas.iterator().next();
		
		List<User> allUsers = userService.getAll();
		for(User u : allUsers) {
			if(u.getUserType().equals("urednik")) {
				for(MagazineScientificArea sa : u.getInterestedAreas()) {
					if(sa.getId() == scientificArea.getId()) {
						System.out.println("Za naucnu oblast (" + scientificArea.getName() + ") pronadjen je aktivan urednik (" + u.getUsername() + ")");
						activeEditorExist = true;
						runtimeService.setVariable(execution.getProcessInstanceId(), "scientificAreaEditor", u.getUsername());
						runtimeService.setVariable(execution.getProcessInstanceId(), "postojiAktivanEditor", true);
						break;
					}
				}
			}
		}
		
		runtimeService.setVariable(execution.getProcessInstanceId(), "postojiAktivanEditor", activeEditorExist);
  
	}


}
