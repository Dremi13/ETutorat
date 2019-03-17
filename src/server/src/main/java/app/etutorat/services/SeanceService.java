package app.etutorat.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import app.etutorat.dao.SeanceRepository;
import app.etutorat.dao.TuteurRepository;
import app.etutorat.dao.TutoreRepository;
import app.etutorat.models.Seance;
import app.etutorat.models.Tuteur;
import app.etutorat.models.Tutore;
import app.etutorat.models.queryobjects.PaireStringLong;


@Service

public class SeanceService {
	
	 @Autowired
	    public TuteurRepository tuteurR;
	 
	 @Autowired
	    public TutoreRepository tutoreR;
	    
	 @Autowired
	 	public SeanceRepository seanceR;
	
	 
	 public List<Seance> listeSeanceTutore(Long id)
	 {
		
		return seanceR.findByIdTutore(id);
		}
	 
	 public List<Seance> listeSeanceTuteur(Long id)
	 {

		return seanceR.findByIdTuteur(id);
	}

	 public List<Seance> findSeancesDisponibles(){
	
		
		 return seanceR.findSeancesDisponibles();
		 
	 }
	 
	 public List<Seance> findAllSeances(){
		 return seanceR.findAllSeances();
	 }
  
  
}
