package app.etutorat.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import app.etutorat.models.Salle;
import app.etutorat.models.Seance;
import app.etutorat.models.queryobjects.ProjectionSeance;
import app.etutorat.models.queryobjects.ProjectionTuteur;
import app.etutorat.models.requestobjects.UserToken;
import app.etutorat.models.requestobjects.forms.SeanceForm;
import app.etutorat.models.requestobjects.forms.UpdateForm;
import app.etutorat.services.SeanceService;
import app.exceptions.FullCourseException;
import app.exceptions.NoElementException;
import app.exceptions.SeanceCollisionException;
import app.exceptions.TooManyHoursException;
import app.exceptions.formException.BadSeanceFormException;
import app.exceptions.formException.BadUpdateFormException;

@Controller
@RequestMapping("")
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200"})
public class SeanceController {

	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private SeanceService ss;
	
	@GetMapping("/getSeances")
	public ResponseEntity<List<ProjectionSeance>> getSeances()  {
		
		
			//Vérification des droits
			if(this.httpSession.getAttribute("token") != null) {
				List<ProjectionSeance> la = ss.getSeances();
				return ResponseEntity.ok(la);
			}
					
			else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not authorized !");		
			}
		
	}
	
	@PostMapping("/createSeance")
	public ResponseEntity<Seance> createSeance(@RequestBody SeanceForm form)  {
		
		System.out.println("Create");
		//Vérification des droits
		if( ((UserToken) this.httpSession.getAttribute("token")).getType().equals("tuteur")) {
			try {
				form.isValid();
			
				Seance s = ss.createSeance(form);
				return ResponseEntity.ok(s);
			}
			catch(BadSeanceFormException ex) {
				throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), ex);
			} 
			catch(SeanceCollisionException ex) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage(), ex);
			}
			catch(TooManyHoursException ex) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage(), ex);
			}
			
		}
				
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not authorized !");		
		}
	
	}
	
	@PostMapping("/updateSeance")
	public ResponseEntity<String> updateSeance(@RequestBody UpdateForm form)  {
		
		System.out.println("Here");
		//Vérification des droits
		if( ((UserToken) this.httpSession.getAttribute("token")).getType().equals("tuteur")) {
			try {
				form.isValid();
			
				ss.updateSeance(form);
				return ResponseEntity.ok("");
			}
			catch(BadUpdateFormException ex) {
				throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), ex);
			} 
			catch(NoElementException ex) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
			}
			catch(SeanceCollisionException ex) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage(), ex);
			}
			catch(TooManyHoursException ex) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage(), ex);
			}
		}
				
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not authorized !");		
		}
	
	}
	
	@PostMapping("/removeSeance")
	public ResponseEntity<String> removeSeance(@RequestBody Long id)  {
		
		System.out.println("Here");
		//Vérification des droits
		if( ((UserToken) this.httpSession.getAttribute("token")).getType().equals("tuteur")) {
			try {
				
				ss.removeSeance(id,((UserToken) this.httpSession.getAttribute("token")).getId());
				return ResponseEntity.ok("");
			}
			catch(NoElementException ex) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
			}
		}
				
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not authorized !");		
		}
	
	}
	
	@PostMapping("/joinSeance")
	public ResponseEntity<String> joinSeance(@RequestBody Long id)  {
		
		System.out.println("Here");
		//Vérification des droits
		if( ((UserToken) this.httpSession.getAttribute("token")).getType().equals("tutore")) {
			try {
				
				ss.joinSeance(id,((UserToken) this.httpSession.getAttribute("token")).getId());
				return ResponseEntity.ok("");
			}
			catch(NoElementException ex) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
			}
			catch(FullCourseException ex) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage(), ex);
			}
		}
				
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not authorized !");		
		}
	
	}
	
	
	
	@GetMapping("/getSalles")
	public ResponseEntity<List<Salle>> getSalles()  {
		
		
		//Vérification des droits
		if(this.httpSession.getAttribute("token") != null) {
			List<Salle> la = ss.getSalles();
			return ResponseEntity.ok(la);
		}
				
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not authorized !");		
		}
	
	}
	
	@GetMapping("/getCurrentTuteur")
	public ResponseEntity<ProjectionTuteur> getCurrentTuteur()  {
		
		
		//Vérification des droits
		if(this.httpSession.getAttribute("token") != null) {
			ProjectionTuteur t = ss.getCurrentTuteur(((UserToken)this.httpSession.getAttribute("token")).getId());
			return ResponseEntity.ok(t);
		}
				
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not authorized !");		
		}
	
	}
}
