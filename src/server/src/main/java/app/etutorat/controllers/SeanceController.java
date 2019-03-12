package app.etutorat.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import app.etutorat.models.Seance;
import app.etutorat.models.Tuteur;
import app.etutorat.models.Tutore;
import app.etutorat.services.AuthentificationService;

import app.etutorat.services.SeanceService;

@Controller
@RequestMapping("/seance")
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200"})
public class SeanceController {
	
	 @Autowired
	  private SeanceService ss;
	 
	 @Autowired 
	 Tuteur tuteur;
	 
	 @Autowired 
	 Tutore tutore;
	 
	 @GetMapping("/tuteur")
	 public List<Seance> getSeancesByTuteur() {
			if (tuteur != null) {
				return ss.findSeanceByTuteur(tuteur);
			}
			return null;
		}
	
	 @GetMapping("/tutore")
	 public List<Seance> getSeancesByTutore() {
			if (tutore != null) {
				return ss.findSeanceByTutore(tutore);
			}
			return null;
		}

	

}
