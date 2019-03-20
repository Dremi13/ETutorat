package app.etutorat.controllers;

import java.security.GeneralSecurityException;
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

import app.etutorat.models.requestobjects.forms.CreateAdminForm;
import app.etutorat.models.requestobjects.forms.EtudiantForm;
import app.etutorat.models.requestobjects.forms.UpdateForm;
import app.etutorat.models.Seance;
import app.etutorat.models.queryobjects.ProjectionAdmin;
import app.etutorat.models.queryobjects.ProjectionTuteur;
import app.etutorat.models.queryobjects.ProjectionTutore;
import app.etutorat.models.requestobjects.MatiereToken;
import app.etutorat.models.requestobjects.UserToken;
import app.etutorat.models.requestobjects.ValidationTuteurToken;
import app.etutorat.services.AdminService;
import app.exceptions.DuplicateUniqueKeyException;
import app.exceptions.NoElementException;
import app.exceptions.formException.BadCreateAdminFormException;
import app.exceptions.formException.BadRegisterFormException;
import app.exceptions.formException.BadUpdateFormException;

@Controller
@RequestMapping("/${admin.path}")
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200"})

public class AdminController {

	@Autowired
	private HttpSession session;
	
	@Autowired
	private AdminService adms;
	
	
	/*
	 *	Partie Admin 
	 */
		
	@PostMapping("/createAdmin")
	public ResponseEntity<List<ProjectionAdmin>> createAdmin(@RequestBody CreateAdminForm form)  {
		  
		System.out.println("CreateAdmin");
		try {
			
			//Vérification des droits
			if(((UserToken)session.getAttribute("token")).getType().equals("superAdmin")) {
				form.isValid();
				List<ProjectionAdmin> la = adms.createAdmin(form);
				return ResponseEntity.ok(la);	  
			}
			
			else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized !");
			}
				  
		}
		catch (BadCreateAdminFormException | GeneralSecurityException ex) {
			System.out.println("createAdmin failed");
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), ex);
		}
		catch(DuplicateUniqueKeyException ex) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage(), ex);
		}
	}
	
	
	
	@GetMapping("/getAllAdmins")
	public ResponseEntity<List<ProjectionAdmin>> getAllAdmins()  {
		
		//Vérification des droits
		if(((UserToken)session.getAttribute("token")).getType().equals("superAdmin")) {
			List<ProjectionAdmin> la = adms.getAllAdmins();
			return ResponseEntity.ok(la);
		}
		
		else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized !");
		}
	}
	
	
	@PostMapping("/updateAdmin")
	public  ResponseEntity<List<ProjectionAdmin>> updateAdmin(@RequestBody UpdateForm form)  {
		

		try {
			//Vérification des droits
			if(((UserToken)session.getAttribute("token")).getType().equals("superAdmin")) {
				form.isValid();
				List<ProjectionAdmin> la = adms.updateAdmin(form);
				return ResponseEntity.ok(la);
			}
			
			else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized !");		
			}
		} 
		catch (BadUpdateFormException | GeneralSecurityException ex) {
			System.out.println("Update failed");
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), ex);
		}
		catch(NoElementException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
		
		catch(DuplicateUniqueKeyException ex) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage(), ex);
		}
	}	
	
	@PostMapping("/removeAdmin")
	public ResponseEntity<List<ProjectionAdmin>> removeAdmin(@RequestBody Long id)  {
		
		try {
			//Vérification des droits
			if(((UserToken)session.getAttribute("token")).getType().equals("superAdmin")) {
				List<ProjectionAdmin> la = adms.removeAdmin(id);
				return ResponseEntity.ok(la);
			}
					
			else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized !");		
			}
		
		}
		catch(NoElementException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}
	
	
	
	/*
	 * Partie Tuteur :
	 * 
	 */
	
	
	
	@PostMapping("/createTuteur")
	public ResponseEntity<List<ProjectionTuteur>> createTuteur(@RequestBody EtudiantForm form)  {
		  
		
		try {
			
			//Vérification des droits
			if( ((UserToken)session.getAttribute("token")).getType().equals("superAdmin")  || ((UserToken)session.getAttribute("token")).getType().equals("admin")   ) {
				form.isValid();
				List<ProjectionTuteur> la = adms.createTuteur(form);
				return ResponseEntity.ok(la);	  
			}
			
			else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized !");
			}
				  
		}
		catch (BadRegisterFormException | GeneralSecurityException ex) {
			System.out.println("createTuteur failed");
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), ex);
		}
		catch(DuplicateUniqueKeyException ex) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage(), ex);
		}
	}
	
	
	
	@GetMapping("/getAllTuteurs")
	public ResponseEntity<List<ProjectionTuteur>> getAllTuteurs()  {
		
		//Vérification des droits
		if( ((UserToken)session.getAttribute("token")).getType().equals("superAdmin")  || ((UserToken)session.getAttribute("token")).getType().equals("admin")   ) {
			List<ProjectionTuteur> la = adms.getAllTuteurs();
			return ResponseEntity.ok(la);
		}
		
		else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized !");
		}
	}
	
	@PostMapping("validationTuteur")
	public ResponseEntity<List<ProjectionTuteur>> validationTuteur(@RequestBody ValidationTuteurToken token)  {
		
		try {
			//Vérification des droits
			if( ((UserToken)session.getAttribute("token")).getType().equals("superAdmin")  || ((UserToken)session.getAttribute("token")).getType().equals("admin")   ) {
				List<ProjectionTuteur> la = adms.validationTuteur(token);
				return ResponseEntity.ok(la);
			}
					
			else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized !");
			}		
		}
		catch(NoElementException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
		
	}
	
	@PostMapping("addCompetenceTuteur")
	public ResponseEntity<List<ProjectionTuteur>> addCompetencesTuteur(@RequestBody MatiereToken token)  {
		
		try {
			//Vérification des droits
			if( ((UserToken)session.getAttribute("token")).getType().equals("superAdmin")  || ((UserToken)session.getAttribute("token")).getType().equals("admin")   ) {
				List<ProjectionTuteur> la = adms.addCompetenceTuteur(token);
				return ResponseEntity.ok(la);
			}
					
			else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized !");
			}	
		}
		catch(NoElementException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
		
	}
	
	@PostMapping("removeCompetenceTuteur")
	public ResponseEntity<List<ProjectionTuteur>> removeCompetenceTuteur(@RequestBody MatiereToken token)  {
		
		try {
			//Vérification des droits
			if( ((UserToken)session.getAttribute("token")).getType().equals("superAdmin")  || ((UserToken)session.getAttribute("token")).getType().equals("admin")   ) {
				List<ProjectionTuteur> la = adms.removeCompetenceTuteur(token);
				return ResponseEntity.ok(la);
			}
					
			else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized !");
			}		
		}
		catch(NoElementException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
		
	}
	
	@PostMapping("updateTuteur")
	public ResponseEntity<List<ProjectionTuteur>> updateTuteur(@RequestBody UpdateForm form)  {
		
		try {
			//Vérification des droits
			if( ((UserToken)session.getAttribute("token")).getType().equals("superAdmin")  || ((UserToken)session.getAttribute("token")).getType().equals("admin")   ) {
				form.isValid();
				List<ProjectionTuteur> la = adms.updateTuteur(form);
				return ResponseEntity.ok(la);
			}
					
			else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized !");
			}		
		}
		catch (BadUpdateFormException | GeneralSecurityException ex) {
			System.out.println("updateTuteur failed");
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), ex);
		}
		catch(NoElementException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
		catch(DuplicateUniqueKeyException ex) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage(), ex);
		}
		
		
	}
	
	
	@PostMapping("/removeTuteur")
	public ResponseEntity<List<ProjectionTuteur>> removeTuteur(@RequestBody Long id)  {
		
		try {
			//Vérification des droits
			if( ((UserToken)session.getAttribute("token")).getType().equals("superAdmin")  || ((UserToken)session.getAttribute("token")).getType().equals("admin")   ) {
				List<ProjectionTuteur> la = adms.removeTuteur(id);
				return ResponseEntity.ok(la);
			}
					
			else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized !");		
			}
		
		}
		catch(NoElementException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}
	
	
	

	/*
	 * Partie Tutore :
	 * 
	 */
	
	
	
	@PostMapping("/createTutore")
	public ResponseEntity<List<ProjectionTutore>> createTutore(@RequestBody EtudiantForm form)  {
		  
		
		try {
			
			//Vérification des droits
			if( ((UserToken)session.getAttribute("token")).getType().equals("superAdmin")  || ((UserToken)session.getAttribute("token")).getType().equals("admin")   ) {
				form.isValid();
				List<ProjectionTutore> la = adms.createTutore(form);
				return ResponseEntity.ok(la);	  
			}
			
			else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized !");
			}
				  
		}
		catch (BadRegisterFormException | GeneralSecurityException ex) {
			System.out.println("createTutore failed");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
		catch(DuplicateUniqueKeyException ex) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage(), ex);
		}
	}
	
	
	
	@GetMapping("/getAllTutores")
	public ResponseEntity<List<ProjectionTutore>> getAllTutores()  {
		
		//Vérification des droits
		if( ((UserToken)session.getAttribute("token")).getType().equals("superAdmin")  || ((UserToken)session.getAttribute("token")).getType().equals("admin")   ) {
			List<ProjectionTutore> la = adms.getAllTutores();
			return ResponseEntity.ok(la);
		}
		
		else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized !");
		}
	}
	
	@PostMapping("addDemandeTutore")
	public ResponseEntity<List<ProjectionTutore>> addDemandesTutore(@RequestBody MatiereToken token)  {
		
		try {
			//Vérification des droits
			if( ((UserToken)session.getAttribute("token")).getType().equals("superAdmin")  || ((UserToken)session.getAttribute("token")).getType().equals("admin")   ) {
				List<ProjectionTutore> la = adms.addDemandeTutore(token);
				return ResponseEntity.ok(la);
			}
					
			else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized !");
			}		
		}
		catch(NoElementException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
		
	}
	
	@PostMapping("removeDemandeTutore")
	public ResponseEntity<List<ProjectionTutore>> removeDemandeTutore(@RequestBody MatiereToken token)  {
		
		try {
			//Vérification des droits
			if( ((UserToken)session.getAttribute("token")).getType().equals("superAdmin")  || ((UserToken)session.getAttribute("token")).getType().equals("admin")   ) {
				List<ProjectionTutore> la = adms.removeDemandeTutore(token);
				return ResponseEntity.ok(la);
			}
					
			else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized !");
			}
		}
		catch(NoElementException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
		
	}
	
	@PostMapping("updateTutore")
	public ResponseEntity<List<ProjectionTutore>> updateTutore(@RequestBody UpdateForm form)  {
		
		try {
			//Vérification des droits
			if( ((UserToken)session.getAttribute("token")).getType().equals("superAdmin")  || ((UserToken)session.getAttribute("token")).getType().equals("admin")   ) {
				form.isValid();
				List<ProjectionTutore> la = adms.updateTutore(form);
				return ResponseEntity.ok(la);
			}
					
			else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized !");
			}		
		}
		catch (BadUpdateFormException | GeneralSecurityException ex) {
			System.out.println("updateTutore failed");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
		catch(NoElementException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
		catch(DuplicateUniqueKeyException ex) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage(), ex);
		}
		
	}
	
	
	@PostMapping("/removeTutore")
	public ResponseEntity<List<ProjectionTutore>> removeTutore(@RequestBody Long id)  {
		
		try {
			//Vérification des droits
			if( ((UserToken)session.getAttribute("token")).getType().equals("superAdmin")  || ((UserToken)session.getAttribute("token")).getType().equals("admin")   ) {
				List<ProjectionTutore> la = adms.removeTutore(id);
				return ResponseEntity.ok(la);
			}
					
			else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not authorized !");		
			}
		}
		catch(NoElementException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}
	
	
	@GetMapping("/getSeances")
	public ResponseEntity<List<Seance>> getSeances()  {
		
		
			//Vérification des droits
			if( ((UserToken)session.getAttribute("token")).getType().equals("superAdmin")  || ((UserToken)session.getAttribute("token")).getType().equals("admin")   ) {
				List<Seance> la = adms.getSeances();
				return ResponseEntity.ok(la);
			}
					
			else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not authorized !");		
			}
		
	}
	
	
	
}
