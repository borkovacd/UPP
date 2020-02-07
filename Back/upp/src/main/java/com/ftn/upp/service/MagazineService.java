package com.ftn.upp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.upp.model.Magazine;
import com.ftn.upp.repository.MagazineRepository;

@Service
public class MagazineService {
	
	@Autowired
	MagazineRepository magazineRepository;

	public List<Magazine> findAll() {
		return magazineRepository.findAll();
	}

}
