package com.ftn.upp.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.upp.model.User;
import com.ftn.upp.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;

	public void saveUser(String username) {
		User user = userRepository.findOneByUsername(username);
		user.setActivated(true);
		userRepository.save(user);
		
	}

	public User findUserByUsername(String username) {
		return userRepository.findOneByUsername(username);
	}
	
	

}
