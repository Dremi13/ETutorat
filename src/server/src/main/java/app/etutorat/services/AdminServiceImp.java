package app.etutorat.services;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.etutorat.dao.AdminRepository;
import app.etutorat.dao.TuteurRepository;
import app.etutorat.dao.TutoreRepository;
import app.etutorat.models.Tuteur;
import app.etutorat.models.Tutore;



@Service
public class AdminServiceImp implements AdminService {
	
	private EntityManager em;
	
	/** Accès aux données */
    @Autowired
    public AdminRepository adminR;

    @Autowired
    public TuteurRepository tuteurR;
    
    @Autowired
    public TutoreRepository tutoreR;


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
		  Optional<Tuteur> tutu = tuteurR.findById(Tuteur.getSerialversionuid());
		  Tuteur tt = tutu.get();
	      tt.setValidationCompte(true);
	   

	}
	
	
	@Override
	public void rejectTuteur(Tuteur tuteur) {
		 Optional<Tuteur> tutu = tuteurR.findById(Tuteur.getSerialversionuid());
		  Tuteur tt = tutu.get();
	      tt.setValidationCompte(false);
		
	}


	@Override
	public List<Tuteur> findAllTuteurs() {
		  return tuteurR.findAll();
	}

	@Override	
	public List<Tutore> findAllTutores() {
		 return tutoreR.findAll();
	}

	
	
}