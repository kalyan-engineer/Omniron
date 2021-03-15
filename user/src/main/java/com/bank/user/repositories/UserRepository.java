package com.bank.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.user.model.User;

public interface UserRepository extends JpaRepository<User, String>{
	
	public User findByuserid(String userid);

}
