package com.ftn.upp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.upp.model.Decision;
import com.ftn.upp.repository.DecisionRepository;

@Service
public class DecisionService {
	
	@Autowired
	private DecisionRepository decisionRepository;

	public List<Decision> findAll() {
		return decisionRepository.findAll();
	}

}
