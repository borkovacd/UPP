package com.ftn.upp.service;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.upp.enums.MemebershipPayment;
import com.ftn.upp.model.FormSubmissionDto;
import com.ftn.upp.model.Magazine;
import com.ftn.upp.repository.MagazineRepository;


@Service
public class SavingMagazineService implements JavaDelegate {
	

	@Autowired
	MagazineRepository magazineRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		  
	      List<FormSubmissionDto> magazine = (List<FormSubmissionDto>)execution.getVariable("magazine");
	      Magazine m = new Magazine();
	      for (FormSubmissionDto formField : magazine) {
			if(formField.getFieldId().equals("naziv")) {
				m.setTitle(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("issn_broj")) {
				m.setIssn(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("otvoren_pristup")) {
				String otvoren_pristup = formField.getFieldValue();
				if(otvoren_pristup.equals("true")) {
					m.setOpenAccess(true);
				} else if(otvoren_pristup.equals("false")) {
					m.setOpenAccess(false);
				}
			}
			
	      }
	      
	      
	      magazineRepository.save(m);
	      
	      
	}
	

}
