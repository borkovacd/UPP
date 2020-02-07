package com.ftn.upp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.upp.model.Coauthor;

public interface CoauthorRepository extends JpaRepository<Coauthor, Long>{
	

}