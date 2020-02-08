package com.ftn.upp.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.upp.service.UserService;
import com.ftn.upp.dto.UserDTO;
import com.ftn.upp.dto.LoginData;
import com.ftn.upp.model.TaskDto;
import com.ftn.upp.model.User;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private TaskService taskService;
	
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
		identityService.setAuthenticatedUserId(loginUser.getUsername());
	
		
		System.out.println("Logged in Camunda user with username: " + identityService.getCurrentAuthentication().getUserId());

        //System.out.println(username + " , " + password);
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
	
	/*@RequestMapping(value="/logout",
			method = RequestMethod.GET)
	public User logoutUser(@Context HttpServletRequest request){
		User user = (User)request.getSession().getAttribute("logged");		
		System.out.println("Usao u funkciju logout");
		
		//System.out.println("Logged in Camunda user with username: " + identityService.getCurrentAuthentication().getUserId());
		
		request.getSession().invalidate();
		identityService.clearAuthentication();
		//System.out.println("Logged in Camunda user with username: " + identityService.getCurrentAuthentication().getUserId());
		if(user == null) {
			return null;
		}
		return user;
	}*/
	
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
	
	
	@RequestMapping(value="/trenutniKorisnik",method = RequestMethod.GET)
	public User trenutniKorisnik(@Context HttpServletRequest request){
		return userService.getCurrentUser();
	}
	
	@RequestMapping(value="/loggedIn", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)	
	public boolean IsUserLoggedIn(){		
		User loggedInUser = userService.getCurrentUser();
		if(loggedInUser != null) {
			return true;
		} else {
			return false;
		}
	}
	
	@GetMapping(path = "/getTasksUser/{username}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<TaskDto>> get(@PathVariable String username) {
		//System.out.println("Usao u getTasksUser");
		System.out.println(username);
		User user = userService.findUserByUsername(username);
		List<TaskDto> dtos = new ArrayList<TaskDto>();
		List<User> allUsers = userService.getAll();
		if(user==null){
			//System.out.println("Null je ");
		}
        for(User u : allUsers){
        	//System.out.println("Username je "+u.getUsername());
        	if(u.getUsername().equals(username)){
        		user=u;
        		//System.out.println("Pronadjen usesr");
        	}
        }
		if(user!=null){
			//System.out.println("User nije null");
		}
		//System.out.println("User nije null");
		List<Task> tasks = new ArrayList<Task>();
		if(user.getUserType().equals("urednik")) {
			//System.out.println("User je urednik");
			tasks.addAll(taskService.createTaskQuery().processDefinitionKey("Proces_ObradeTeksta").taskAssignee(user.getUsername()).list());
		}
		for (Task task : tasks) {
			TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee());
			dtos.add(t);
		}
		
        return new ResponseEntity<List<TaskDto>>(dtos,  HttpStatus.OK);
    }
	
	

}
