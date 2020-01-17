package com.ftn.upp.service;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.upp.model.FormSubmissionDto;
import com.ftn.upp.model.Magazine;
import com.ftn.upp.model.ScientificArea;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.MagazineRepository;
import com.ftn.upp.repository.UserRepository;

@Service
public class MagazineActivationService implements JavaDelegate {
	
	@Autowired
	MagazineRepository magazineRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		  
	      List<FormSubmissionDto> magazine = (List<FormSubmissionDto>)execution.getVariable("magazine");
	  
	      String magazineISSN = "";
	      for (FormSubmissionDto formField : magazine) {
				if(formField.getFieldId().equals("issn_broj")) {
					magazineISSN = formField.getFieldValue();
				}
		  }
	      
	      Magazine casopis = magazineRepository.findOneByIssn(magazineISSN);
	      casopis.setActive(true);
	     
	      
	      magazineRepository.save(casopis);
	}

}
