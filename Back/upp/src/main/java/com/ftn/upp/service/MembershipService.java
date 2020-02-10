package com.ftn.upp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.upp.model.Membership;
import com.ftn.upp.repository.MembershipRepository;

@Service
public class MembershipService {

	@Autowired
	MembershipRepository membershipRepository;

	public List<Membership> findAll() {
		return membershipRepository.findAll();
	}
}
