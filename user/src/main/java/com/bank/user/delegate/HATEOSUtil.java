package com.bank.user.delegate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bank.user.DTO.UserDTO;
import com.bank.user.model.Account;

@Component
public class HATEOSUtil {

	public List<String> allowedUserStates(String role){

        List<String> actions = new ArrayList<>();
        if(null !=role &&  role.equalsIgnoreCase("manager"))
        {
            actions.add("getall");
            actions.add("delete");
            actions.add("getone");
            actions.add("save");
            actions.add("self");
        }
        else
        {
        	 actions.add("self");
        }

        return actions;
    }
}
