package com.bank.user.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.bank.user.DTO.UserDTO;
import com.bank.user.model.User;

@Component
public class Helper {
	
	/*
	 * @Autowired AccountRepository accountRepository;
	 */

	public User FromDto(UserDTO userDTO) throws ParseException{
        User user = new User();
        user.setUserid(userDTO.getUserid());
        user.setUsername(userDTO.getUserid());
        user.setPassword(userDTO.getPassword());
        String dateOfBirth = userDTO.getDateOfBirth();
		/*
		 * DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
		 * LocalDate dt = formatter.parseLocalDate(dateOfBirth);
		 */
        Date birthDate=new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirth);  
        user.setBirthday(birthDate);
        user.setEmail(userDTO.getEmail());
        user.setMobile(userDTO.getMobile());
        user.setRoleid(userDTO.getRole());        
        return user;
    } 
	
	public UserDTO fromDomain(User user) {
		UserDTO userDTO = new UserDTO();
		//userDTO.setUserid(user.getUserid());
		userDTO.setEmail(user.getEmail());
		userDTO.setMobile(user.getMobile());
		userDTO.setRole(user.getRoleid());
		userDTO.setUsername(user.getUsername());
		return userDTO;
	}
	
	/*
	 * public static String getToken() { String token = null; Authentication
	 * authentication =
	 * SecurityContextHolder.getContext().getAuthentication().getDetails(); if
	 * (authentication != null) { Object details = authentication.getDetails();
	 * token = ((OAuth2AuthenticationDetails)
	 * authentication.getDetails()).getTokenValue(); } return token; }
	 */
	
	/*
	 * public Account FromDto(AccountDTO accountDTO){ String accountid =
	 * accountDTO.getAccountid(); Account account =null; if(null != accountid &&
	 * accountid.equalsIgnoreCase("")) { try { account =
	 * accountRepository.getOne(accountid); }catch(EntityNotFoundException e) {
	 * account = new Account(); account.setAccountid(accountid);
	 * //account.setCreateddate(new DateTime()); }
	 * 
	 * }else { account = new Account(); account.setAccountid("98728");
	 * //account.setCreateddate(new DateTime()); }
	 * account.setAccounttype(accountDTO.getAccounttype());
	 * account.setBranch(accountDTO.getBranch()); //DateTimeFormatter formatter =
	 * DateTimeFormat.forPattern("dd/mm/yyyy"); //DateTime parse =
	 * DateTime.parse(accountDTO.getDateofbirth(), formatter); //
	 * account.setDateofbirth(parse);
	 * 
	 * // account.setMinor(isMinor(parse));
	 * 
	 * return account; }
	 * 
	 * Boolean isMinor(DateTime dob) { long dobMilliSeconds = dob.getMillis(); long
	 * currentMilliSeconds = new Date().getTime(); long currentAge =
	 * currentMilliSeconds-dobMilliSeconds; float age =
	 * currentAge/1000*60*60*24*30*12; System.out.println("age is::"+age);
	 * if(age>18) { return false; }else { return true; }
	 * 
	 * }
	 */
}
