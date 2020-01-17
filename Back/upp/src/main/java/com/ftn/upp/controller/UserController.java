package com.ftn.upp.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.upp.service.UserService;
import com.ftn.upp.dto.UserDTO;
import com.ftn.upp.dto.LoginData;
import com.ftn.upp.model.User;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/loginUser",method = RequestMethod.POST)
    public  ResponseEntity<User>  loginUser(@RequestBody LoginData loginData, @Context HttpServletRequest request){
       String encryptedPass = "";
       String username = loginData.getUsername();
       String password = loginData.getPassword();

       User loginUser = userService.findUserByUsername(username);
       if(loginUser == null){
    	    System.out.println("Ne postoji username");
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
       }
	   if(!loginUser.isActivated()){
   	        System.out.println("Nije aktiviran");
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);

	   } 
	   	/*try {
			encryptedPass = userService.encrypt(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!loginUser.getPassword().equals(encryptedPass)) {
			//moraju se poklapati unesena lozinka i lozinka od korisnika sa unetim mailom 
			System.out.println("Ne poklapaju se sifre");
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}*/
	   
	   if(!loginUser.getPassword().equals(password)) {
			//moraju se poklapati unesena lozinka i lozinka od korisnika sa unetim mailom 
			System.out.println("Ne poklapaju se sifre");
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);

		}
		
		request.getSession().setAttribute("logged", loginUser);

        System.out.println(username + " , " + password);
		return new ResponseEntity<User>(loginUser, HttpStatus.OK);
    }
	
	@RequestMapping(value="/loginAdmin",method = RequestMethod.POST)
    public  ResponseEntity<User>  loginAdmin(@RequestBody LoginData loginData, @Context HttpServletRequest request){
       String encryptedPass = "";
       String username = loginData.getUsername();
       String password = loginData.getPassword();

       User loginUser = userService.findUserByUsername(username);
       if(loginUser == null){
    	    System.out.println("Ne postoji username");
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
       }
	   	/*try {
			encryptedPass = userService.encrypt(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!loginUser.getPassword().equals(encryptedPass)) {
			//moraju se poklapati unesena lozinka i lozinka od korisnika sa unetim mailom 
			System.out.println("Ne poklapaju se sifre");
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);

		}*/
	   
	   if(!loginUser.getPassword().equals(password)) {
			//moraju se poklapati unesena lozinka i lozinka od korisnika sa unetim mailom 
			System.out.println("Ne poklapaju se sifre");
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);

		}
	   	if(!loginUser.getUserType().equals("admin")) {
	   		//moraju biti admin
			System.out.println("Nije admin");
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
	   	}
		
		request.getSession().setAttribute("logged", loginUser);

        System.out.println(username + " , " + password);
		return new ResponseEntity<User>(loginUser, HttpStatus.OK);
    }
	
	@RequestMapping(value="/logout",
			method = RequestMethod.GET)
	public User logoutUser(@Context HttpServletRequest request){
		User user = (User)request.getSession().getAttribute("logged");		
		System.out.println("Usao u funkciju logout");
		
		request.getSession().invalidate();
		if(user == null) {
			return null;
		}
		return user;
	}
	
	@RequestMapping(value="/getReviewers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<List<UserDTO>> getAllReviewers(){		
		System.out.println("Usao u getAllReviewers");
		List<User> users = userService.getAll();
		List<User> reviewers = new ArrayList<User>();
		for(User u : users){
			if(u.getUserType().equals("recenzent")){
				reviewers.add(u);
			}
		}
		List<UserDTO> list=new ArrayList<UserDTO>();
		for(User e : reviewers){
			list.add(new UserDTO(e.getId(),e.getFirstName(),e.getLastName()));
		}
		return new ResponseEntity<List<UserDTO>>(list, HttpStatus.OK);
	}
	@RequestMapping(value="/getEditors", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<List<UserDTO>> getAllEditors(){		
		List<User> users = userService.getAll();
		System.out.println("Usao u get all editors");
		
		List<User> editors = new ArrayList<User>();
		for(User u : users){
			if(u.getUserType().equals("urednik")){
					editors.add(u);
				}
		}
		List<UserDTO> list=new ArrayList<UserDTO>();
		for(User e : editors){
			list.add(new UserDTO(e.getId(),e.getFirstName(),e.getLastName()));
		}
		return new ResponseEntity<List<UserDTO>>(list, HttpStatus.OK);
	}
	
	

}
