package com.ftn.upp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.upp.model.User;

public interface UserRepository extends JpaRepository<User, Long>{


	User findOneByUsername(String username);

	

}
