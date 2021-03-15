package com.bank.user.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bank.user.DTO.AllUsers;
import com.bank.user.DTO.Pagination;
import com.bank.user.DTO.UserDTO;
import com.bank.user.delegate.HATEOSUtil;
import com.bank.user.delegate.UserDelegate;
import com.bank.user.handler.Helper;
import com.bank.user.model.User;
import com.bank.user.repositories.UserRepository;
import com.bank.user.utils.TokenUtils;

@RestController
@RequestMapping("/user")
public class UserServiceImpl {
	
	@Autowired
	UserDelegate userDelegate;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	Helper helper;
	
	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	HATEOSUtil hateosUtil;
	
	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value="/register", method = RequestMethod.POST)
    public ResponseEntity<?>  saveUser(@RequestBody UserDTO userDTO){
		UserDTO savedUser = userDelegate.save(userDTO);
		EntityModel<UserDTO> resource = EntityModel.of(savedUser); 
		List<String> allowedUserStates = hateosUtil.allowedUserStates(userDTO.getRole());
		allowedUserStates.stream().forEach(state ->{
	           if(state.equalsIgnoreCase("self")) {
	        	   resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).saveUser(new UserDTO())).withRel("self"));
	           }if(state.equalsIgnoreCase("getall")) {
	        	   resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllAccountHolders(new AllUsers())).withRel("getAllUser/AccountHolders"));
	           }if(state.equalsIgnoreCase("getone")) {
	        	   resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUser(new String(savedUser.getUserid()))).withRel("getUserById"));
	           }
				
	        });
		 return new ResponseEntity<>(resource, HttpStatus.OK);
        //return savedUser;
    }

	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?>  getUser(@PathVariable("id") String id) {
		UserDTO userDTO = userDelegate.getUserById(id);
		EntityModel<UserDTO> resource = EntityModel.of(userDTO);
		List<String> allowedUserStates = hateosUtil.allowedUserStates(userDTO.getRole());
		allowedUserStates.stream().forEach(state ->{
			if(state.equalsIgnoreCase("self")) {
	        	   resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUser(id)).withRel("self"));
	           }if(state.equalsIgnoreCase("getall")) {
	        	   resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllAccountHolders(new AllUsers())).withRel("getAllUser/AccountHolders"));
	           }if(state.equalsIgnoreCase("save")) {
	        	   resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).saveUser(new UserDTO())).withRel("Save user"));
	           }
		});
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/loggeduser",method = RequestMethod.GET)
	public UserDTO getloggedInUser(@RequestHeader (name="Authorization") String token) {
		String jwt = token.substring(7);
		String id = tokenUtils.extractUsername(jwt);
		UserDTO userDTO = userDelegate.getUserById(id);
		return userDTO;
	}
	
	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
		String deleteuser = userDelegate.deleteuser(id);
		EntityModel<String> resource = EntityModel.of(deleteuser);
		/*
		 * Building Hateos Links
		 */
		List<String> allowedUserStates = hateosUtil.allowedUserStates("MANAGER");
		allowedUserStates.stream().forEach(state ->{
			if(state.equalsIgnoreCase("self")) {
	        	   resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).deleteUser(id)).withRel("self"));
	           }if(state.equalsIgnoreCase("getall")) {
	        	   resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllAccountHolders(new AllUsers())).withRel("getAllUser/AccountHolders"));
	           }if(state.equalsIgnoreCase("save")) {
	        	   resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).saveUser(new UserDTO())).withRel("Save user"));
	           }if(state.equalsIgnoreCase("getone")) {
	        	   resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUser(id)).withRel("get user"));
	           }
		});
		
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value = "/all", method = RequestMethod.POST)
	public ResponseEntity<?> getAllAccountHolders(@RequestBody AllUsers users) {
		Pagination page = users.getPage();
		Pageable pageable = null;
		if(null == page) {
			pageable = PageRequest.of(0, 10);
		}else {
			int pageNum = page.getPage();
			int pageSize = page.getSize();
			pageable = PageRequest.of(pageNum, pageSize);
		}
		
		Page<User> allUsers = userRepo.findAll(pageable);
		List<UserDTO> usersDTO = new ArrayList<>();
		if(null != allUsers) {
			long totalElements = allUsers.getTotalElements();
			users.setTotalRecords(totalElements);
			List<User> usersList = allUsers.toList();
			for(User user :usersList ) {
				UserDTO fromDomain = helper.fromDomain(user);
				usersDTO.add(fromDomain);
			}
			users.setUserRecords(usersDTO);
			
		}
		EntityModel<AllUsers> resource = EntityModel.of(users);
		/*
		 * Building Hateos Links
		 */
		List<String> allowedUserStates = hateosUtil.allowedUserStates("MANAGER");
		allowedUserStates.stream().forEach(state ->{
			if(state.equalsIgnoreCase("self")) {
	        	   resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllAccountHolders(new AllUsers())).withRel("self"));
	           }if(state.equalsIgnoreCase("getall")) {
	        	   resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllAccountHolders(new AllUsers())).withRel("getAllUser/AccountHolders"));
	           }if(state.equalsIgnoreCase("save")) {
	        	   resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).saveUser(new UserDTO())).withRel("Save user"));
	           }
		});
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

}
