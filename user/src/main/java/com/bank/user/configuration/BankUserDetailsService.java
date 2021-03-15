package com.bank.user.configuration;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.bank.user.DTO.UserDTO;
import com.bank.user.handler.Helper;
import com.bank.user.model.User;
import com.bank.user.repositories.UserRepository;

@Component
public class BankUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	Helper helper;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = findUser(username);
		if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
		
	}

	 private Set<SimpleGrantedAuthority> getAuthority(User user) {
	        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
	       String roleid = user.getRoleid();
	       if(null != roleid) {
	    	   authorities.add(new SimpleGrantedAuthority("ROLE_" + roleid));
	       }
	        return authorities;
	    }
	 
    public User findUser(String userid) {
        return userRepo.findByuserid(userid);
    }
	
    public User save(UserDTO userDTO) {
        User nUser = null;
		try {
			nUser = helper.FromDto(userDTO);
			nUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
	        nUser.setRoleid(userDTO.getRole());
	        
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User user = userRepo.save(nUser);
        return user;
    }
}
