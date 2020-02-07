package com.ftn.upp.service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

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

	public void activateUser(String username) {
		User user = userRepository.findOneByUsername(username);
		user.setActivated(true);
	}
	
	public List<User> getAll() {
		return userRepository.findAll();
	}
	
	public User getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User u = userRepository.findOneByUsername(principal.toString());
		return u;
	}

	
	

}
