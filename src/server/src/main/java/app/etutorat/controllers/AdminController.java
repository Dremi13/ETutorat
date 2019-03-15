package app.etutorat.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.etutorat.dao.AdminRepository;
import app.etutorat.dao.TuteurRepository;
import app.etutorat.dao.TutoreRepository;
import app.etutorat.models.Tuteur;
import app.etutorat.models.Tutore;
import app.etutorat.services.AdminService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200"})
public class AdminController {
		
	@Autowired
	AdminService as ;

	@Autowired
	AdminRepository adminr ;
	
	@Autowired
	TuteurRepository tuteurr ;
	
	@Autowired
	TutoreRepository tutorer ;
	
	
	  @GetMapping("/allTuteurs")
	  List<Tuteur> findAllTuteurs() {
			return as.findAllTuteurs();
	  }
	  
	  @GetMapping("/allTutores")
	  List<Tutore> findAllTutores() {
			return as.findAllTutores();
	  }
	  
	  @DeleteMapping("/deleteTutore/{idTutore}")
		void deleteTutore(@PathVariable Long idTutore) {
			as.deleteTutore(idTutore);
		}
	  
	  @DeleteMapping("/deleteTuteur/{idTuteur}")
		void deleteTuteur(@PathVariable Long idTuteur) {
			as.deleteTuteur(idTuteur);
		}
	
	  // le add  a regarder plus tard
	//  @PostMapping("/addTuteur")
	
	/*  void addTuteur(@RequestBody Tuteur newtuteur) {
			 as.addTuteur(newtuteur);*/
		//}
	  
	  
	 // @PostMapping("/addTutore")
		/*void addTutore(@RequestBody Tutore newtutore) {
			 as.addTutore(newtutore);*/
			
		//}
	  
	 /*  GET OR POST POUR VALIDER REJETER a VOIR */
	  
}
