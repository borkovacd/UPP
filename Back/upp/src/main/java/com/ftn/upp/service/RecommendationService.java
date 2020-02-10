package com.ftn.upp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.upp.model.Recommendation;
import com.ftn.upp.repository.RecommendationRepository;

@Service
public class RecommendationService {
	
	@Autowired
	RecommendationRepository recommendationRepository;

	public List<Recommendation> findAll() {
		return recommendationRepository.findAll();
	}

}
