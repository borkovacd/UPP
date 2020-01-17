package com.ftn.upp.service;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.upp.model.FormSubmissionDto;
import com.ftn.upp.model.Magazine;
import com.ftn.upp.model.MagazineScientificArea;
import com.ftn.upp.model.ScientificArea;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.MagazineRepository;
import com.ftn.upp.repository.MagazineScientificAreaRepository;


@Service
public class SavingMagazineScientificAreaService implements JavaDelegate {

	
	@Autowired
	MagazineScientificAreaRepository magazineScientificAreaRepository;
	@Autowired
	MagazineRepository magazineRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		  
	      List<FormSubmissionDto> scientificArea = (List<FormSubmissionDto>) execution.getVariable("magazineScientificArea");
	      MagazineScientificArea msa = new MagazineScientificArea();
	      for (FormSubmissionDto formField : scientificArea) {
			if(formField.getFieldId().equals("naucna_oblast")) {
				msa.setName(formField.getFieldValue());
			}
	      }
	      
	      List<FormSubmissionDto> magazine = (List<FormSubmissionDto>)execution.getVariable("magazine");
	      String magazineISSN = "";
	      for (FormSubmissionDto formField : magazine) {
				if(formField.getFieldId().equals("issn_broj")) {
					magazineISSN = formField.getFieldValue();
				}
		      }
	      
	      magazineScientificAreaRepository.save(msa);
	      
	      Magazine casopis = magazineRepository.findOneByIssn(magazineISSN);
	      casopis.getMagazineAreas().add(msa);
	      
	      magazineRepository.save(casopis);
	      
	}
}

