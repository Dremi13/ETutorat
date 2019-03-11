package app.etutorat.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.stereotype.Controller;


import org.springframework.web.server.ResponseStatusException;

import app.etutorat.models.requestobjects.UserToken;
import app.etutorat.models.requestobjects.RegisterTuteurForm;
import app.etutorat.models.requestobjects.SigninForm;
import app.etutorat.services.AuthentificationService;
import app.exceptions.WrongLoginPasswordException;
import app.exceptions.BadRegisterFormException;
import app.exceptions.UserNotSignedInException;

@Controller
@RequestMapping("/auth")
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200"})

public class AuthentificationController {

	
	  @Autowired
	  private AuthentificationService as;


	  
	  @GetMapping("/check")
	  public ResponseEntity<UserToken> checkSignin() {
	        
		  
		  
		  try {
			  
			  UserToken token = as.checkSignin();
			  System.out.println("checkSign OK !");
			  return ResponseEntity.ok(token);
		  }
		  
		  catch (UserNotSignedInException ex) {
			  System.out.println("checkSign failed");
			  throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not signed in", ex);
		  }
		  
	    }
	  
	  @PostMapping("/signin")
	  public ResponseEntity<UserToken> signin(@RequestBody SigninForm form){
		  
		  try {
			  System.out.println("Sign in OK");
			  UserToken token = as.signin(form);
			  return ResponseEntity.ok(token);
			  
		  }
		  catch (WrongLoginPasswordException ex) {
			  System.out.println("Sign in failed");
			  throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong login/password", ex);
		  }
	  
	  }
	  
	  
	  @GetMapping("/signout")
	  public ResponseEntity<String> signout(){
		  
		  System.out.println("Sign out");
		  try {
			  as.signout();
			  return ResponseEntity.ok("");
		  }
		  catch (UserNotSignedInException ex) {
			  throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not signed in", ex);
		  }
	  
	  }
	  
	  @PostMapping("/register/tuteur")
	  public ResponseEntity<UserToken> register(@RequestBody RegisterTuteurForm form) {
		  
		  System.out.println("Sign up");
		  try {
			  UserToken token = as.register(form);
			  return ResponseEntity.ok(token);
			  
		  }
		  catch (BadRegisterFormException ex) {
			  System.out.println("Sign in failed");
			  throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong login/password", ex);
		  }
		  
		  
	  }
	  
	  
	  
	  
	
}
