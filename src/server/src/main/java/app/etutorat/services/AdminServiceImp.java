package app.etutorat.services;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import app.etutorat.dao.AdminRepository;
//import app.etutorat.dao.EtudiantRepository;
import app.etutorat.dao.SalleRepository;
import app.etutorat.dao.SeanceRepository;
import app.etutorat.dao.TuteurRepository;
import app.etutorat.dao.TutoreRepository;
//import app.etutorat.dao.UtilisateurRepository;
import app.etutorat.models.Tuteur;
import app.etutorat.models.Tutore;
import app.etutorat.models.Utilisateur;



@Service
public class AdminServiceImp implements AdminService {
	
	/** Accès aux données */
    @Autowired
    public AdminRepository adminR;
    
   /* @Autowired
    public EtudiantRepository er;
    */
    @Autowired
    public TuteurRepository tuteurR;
    @Autowired
    public TutoreRepository tutoreR;
    @Autowired
    public SalleRepository salleR;
    @Autowired
    public SeanceRepository seanceR;
 
	@PersistenceContext
	private EntityManager em;
	


	@Override
	public List<Utilisateur> getAllUtilisateur() {
		//return er.findAll();
		return null;
	}


	@Override
	public void rejectTuteur(Tuteur tuteur) {
		if(tuteur.isValidationCompte() == true )
			tuteur.setValidationCompte(false);
		
	}


	@Override
	public void addTuteur(Tuteur tuteur) {
		tuteurR.save(tuteur);
	}


	@Override
	public void deleteTuteur(Long idTuteur) {
		tuteurR.deleteById(idTuteur);
		
	}


	@Override
	public void addTutore(Tutore tutore) {
		tutoreR.save(tutore);
	}


	@Override
	public void deleteTutore(long idTutore) {
		tutoreR.deleteById(idTutore);
		
	}


	@Override
	public void validAndUpdateTuteur(Tuteur tuteur) {
		if(tuteur.isValidationCompte() == false )
			tuteur.setValidationCompte(true);
	}
	
	@Override
	public List<Tuteur> getTuteur() {
		return tuteurR.findAll();
	}

	@Override
	public List<Tutore> getTutore() {
		return tutoreR.findAll();
	}


	
	
}