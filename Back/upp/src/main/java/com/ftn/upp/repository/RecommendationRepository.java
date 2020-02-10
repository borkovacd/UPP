package com.ftn.upp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.upp.model.Recommendation;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long>{

}