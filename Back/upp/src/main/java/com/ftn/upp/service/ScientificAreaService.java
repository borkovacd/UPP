package com.ftn.upp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.upp.model.MagazineScientificArea;
import com.ftn.upp.repository.MagazineScientificAreaRepository;

@Service
public class ScientificAreaService {
	
	@Autowired
	MagazineScientificAreaRepository saRepository;

	public List<MagazineScientificArea> findAll() {
		return saRepository.findAll();
	}

}
