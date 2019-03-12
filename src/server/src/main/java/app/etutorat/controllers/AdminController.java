package app.etutorat.controllers;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.etutorat.dao.UtilisateurRepository;
import app.etutorat.models.Tuteur;
import app.etutorat.models.Tutore;
import app.etutorat.services.AdminService;

@Controller
public class AdminController {
		
	@Autowired
	AdminService as ;
	
	@Autowired
	UtilisateurRepository us ;
	
	@RequestMapping(value = "/addTutore", method = RequestMethod.POST)
    	public String addTutore(HttpServletRequest request, Map<String, Object> model){
		Tutore t = new Tutore();
		as.addTutore(t);
		model.put("tutore", as.getTutore());
    	return "homepage";
    }
	
	@RequestMapping(value = "/addTuteur", method = RequestMethod.POST)
		public String addTuteur(HttpServletRequest request, Map<String, Object> model){
		Tuteur t = new Tuteur();
		as.addTuteur(t);
		model.put("tuteur", as.getTuteur());
		return "homepage";
}

	
	@RequestMapping( value = "/removeTuteur", method= RequestMethod.POST)
	public String removeTuteur( HttpServletRequest request, Map<String, Object> model) {
		Long id = Long.valueOf(request.getParameter("IdTuteur"));
		as.deleteTuteur(id);
		model.put("tuteur", as.getTuteur());
		return "liste";
	}
	
	@RequestMapping( value = "/removeTutore", method= RequestMethod.POST)
	public String removeTutore( HttpServletRequest request, Map<String, Object> model) {
		Long id = Long.valueOf(request.getParameter("IdTutore"));
		as.deleteTutore(id);
		model.put("tutore", as.getTutore());
		return "liste";
	}
	
	
	@RequestMapping( value = "/liste", method= RequestMethod.POST)
	public String liste( HttpServletRequest request, Map<String, Object> model) {
	//	log.info("Liste = " + request.getParameter("typeUtil"));
		if(request.getParameter("typeUtil").equals("tuteur"))
			model.put("tuteur", as.getTuteur());
		else {
			model.put("tutore", as.getTutore());
		}
		return "homepage";
	}
	
	/// .à plus tard : ajouter une methode de findby peut etre
	@RequestMapping( value = "/valideCompte", method= RequestMethod.POST)
	public String valideCompte( HttpServletRequest request, Map<String, Object> model) {
		/*Tuteur t = us.findByEmail(request.getParameter("tuteur"));
		as.validAndUpdateTuteur(request.getParameter("tuteur")); */
		return "liste"; 
		
	}
	
	@RequestMapping( value = "/rejectCompte", method= RequestMethod.POST)
	public String rejectCompte( HttpServletRequest request, Map<String, Object> model) {
		
		return "liste";
		
	}

	
	
	
}
