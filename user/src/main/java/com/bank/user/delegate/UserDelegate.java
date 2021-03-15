package com.bank.user.delegate;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.bank.user.DTO.UserDTO;
import com.bank.user.handler.Helper;
import com.bank.user.model.User;
import com.bank.user.repositories.UserRepository;

@Component
public class UserDelegate {
	
	@Autowired
	Helper helper;

	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepo;
	
	public UserDTO save(UserDTO userDTO) {
		if(null == userDTO || null == userDTO.getUserid() || userDTO.getUserid().equalsIgnoreCase("")) {
			return null;
		}
		//User user = userRepo.findByuserid(userDTO.getUserid());
		User user;
		try {
			user = helper.FromDto(userDTO);
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			user = userRepo.save(user);
			userDTO.setUserid(user.getUserid());
			userDTO.setUsername(user.getUserid());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userDTO;
	}
	
	public UserDTO getUserById(String id) {
		User user = userRepo.findByuserid(id);
		UserDTO userDTO = helper.fromDomain(user);
		return userDTO;
	}
	
	public String deleteuser(String id) {
		if(null == id || id.equalsIgnoreCase("")) {
			return "failure";
		}
		User user = userRepo.findByuserid(id);
		if(null == user) {
			return "failure";
		}
		user.setStatus("Deleted");
		return "success";
	}
}
