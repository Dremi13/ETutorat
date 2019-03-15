package app.etutorat.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.etutorat.dao.SeanceRepository;
import app.etutorat.dao.TuteurRepository;
import app.etutorat.models.Seance;
import app.etutorat.models.Tuteur;
import app.etutorat.models.Tutore;
import app.etutorat.services.SeanceService;

@RestController
@RequestMapping("/tuteur")
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200"})
public class SeanceController {
	
	 @Autowired
	  SeanceService seanceS;
	 
	 
	 @Autowired
	  TuteurRepository tuteurR;
	 
	 
	
	 @GetMapping("/seanceTutore")
	 public  List<Seance> findAllSeanceT(Tutore tutore) {
	  return seanceS.listeSeance(tutore);
	 
	 }
	 
	  @GetMapping("/seanceTuteur")
		 public  List<Seance> findAllSeanceT(Tuteur tuteur) {
		  return seanceS.listeSeancet(tuteur);
		}
	


}