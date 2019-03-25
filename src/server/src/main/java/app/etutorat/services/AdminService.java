package app.etutorat.services;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.etutorat.dao.AdminRepository;
import app.etutorat.dao.SalleRepository;
import app.etutorat.dao.SeanceRepository;
import app.etutorat.dao.TuteurRepository;
import app.etutorat.dao.TutoreRepository;
import app.etutorat.models.Administrateur;
import app.etutorat.models.Salle;
import app.etutorat.models.Seance;
import app.etutorat.models.Tuteur;
import app.etutorat.models.Tutore;
import app.etutorat.models.queryobjects.ProjectionAdmin;
import app.etutorat.models.queryobjects.ProjectionSeance;
import app.etutorat.models.queryobjects.ProjectionTuteur;
import app.etutorat.models.queryobjects.ProjectionTutore;
import app.etutorat.models.requestobjects.MatiereToken;
import app.etutorat.models.requestobjects.ValidationTuteurToken;
import app.etutorat.models.requestobjects.forms.CreateAdminForm;
import app.etutorat.models.requestobjects.forms.EtudiantForm;
import app.etutorat.models.requestobjects.forms.UpdateForm;
import app.exceptions.DuplicateUniqueKeyException;
import app.exceptions.FullCourseException;
import app.exceptions.NoElementException;

import static app.etutorat.utils.HashPswd.*;

@Service
@Transactional
public class AdminService {

	
	
	@Autowired
	private AdminRepository ar;
	
	@Autowired
	private TuteurRepository tur;
	
	@Autowired
	private TutoreRepository tor;
	
	
	@Autowired
	private SeanceRepository ser;
	
	@Autowired
	private SalleRepository salr;
	
	
	public List<ProjectionAdmin> createAdmin(CreateAdminForm form) throws GeneralSecurityException, DuplicateUniqueKeyException {
		
		//check
		Administrateur otherEmail = (Administrateur) ar.findByEmail(form.getEmail());
		if(otherEmail != null) throw new DuplicateUniqueKeyException("email");

	    byte[][] hash = hashPassword(form.getPassword());
	    Administrateur a = new Administrateur(form.getNom(),form.getPrenom(),form.getEmail(),hash[1],hash[0]);
	    ar.save(a);
	
	    return this.getAllAdmins();
       
	}
	
	
	public List<ProjectionAdmin> updateAdmin(UpdateForm form) throws GeneralSecurityException, NoElementException, DuplicateUniqueKeyException {
		
		Optional<Administrateur> a = ar.findById(form.getId());
		if(!a.isPresent()) throw new NoElementException(form.getId());
		
		Administrateur b = a.get();
		CreateAdminForm f = (CreateAdminForm) form.getForm();
		
		
		//check :
		Administrateur otherEmail = (Administrateur) ar.findByEmail(f.getEmail());
		if(otherEmail != null) throw new DuplicateUniqueKeyException("email");
		
		
		
		byte[][] hash = hashPassword(f.getPassword());
		
		b.setNom( f.getNom() );
		b.setPrenom( f.getPrenom() );
		b.setEmail( f.getEmail() );
		b.setSalt(hash[0]);
		b.setPassword(hash[1]);
		
		ar.save(b);
		
		return this.getAllAdmins();
		
	}
	
	
	public List<ProjectionAdmin> removeAdmin(Long id) throws NoElementException{
		
		//check
		Optional<Administrateur> a = ar.findById(id);
		if(!a.isPresent()) throw new NoElementException(id);
		
		ar.deleteById(id);
		return this.getAllAdmins();
	}
	
	
	public List<ProjectionAdmin> getAllAdmins(){
		return ar.findBy();
	}
	
	
	
	
	
	
	
	
	
	
	
	/*
	 *  Partie Tuteur
	 */
	
	public List<ProjectionTuteur> createTuteur(EtudiantForm form) throws GeneralSecurityException, DuplicateUniqueKeyException {
		

		//check
		boolean exception = false;
		String errorMessage = "";
		
		Tuteur otherTuteur = (Tuteur) tur.findByEmail(form.getEmail());
		Tutore otherTutore = (Tutore) tor.findByEmail(form.getEmail());
		if(otherTuteur != null || otherTutore != null) {
			exception = true;
			errorMessage += "/email";
		}
		
		otherTuteur = (Tuteur) tur.findByCodeetu(form.getCodeetu());
		otherTutore = (Tutore) tor.findByCodeetu(form.getCodeetu());
		if(otherTuteur != null || otherTutore != null) {
			exception = true;
			errorMessage += "/codeetu";
		}
		
		if(exception) throw new DuplicateUniqueKeyException(errorMessage);
		
        byte[][] hash = hashPassword(form.getPassword());
        Tuteur t = new Tuteur(form.getNom(),form.getPrenom(),form.getEmail(),hash[1],hash[0],form.getCodeetu(),form.getTelephone(),form.getFiliere(),false);
	    tur.save(t);
	        
	    return this.getAllTuteurs();

	}
	
	
	public List<ProjectionTuteur> updateTuteur(UpdateForm form) throws GeneralSecurityException, NoElementException, DuplicateUniqueKeyException {
		
		EtudiantForm trueForm = (EtudiantForm) form.getForm();
		
		//check :
		Optional<Tuteur> ot = tur.findById(form.getId());
		if(!ot.isPresent()) throw new NoElementException(form.getId());
		
		boolean exception = false;
		String errorMessage = "";
		
		Tuteur otherTuteur = (Tuteur) tur.findByEmail(trueForm.getEmail());
		Tutore otherTutore = (Tutore) tor.findByEmail(trueForm.getEmail());
		if( (otherTuteur != null && otherTuteur.getId() != form.getId()) || otherTutore != null) {
			exception = true;
			errorMessage += "/email";
		}
		
		otherTuteur = (Tuteur) tur.findByCodeetu(trueForm.getCodeetu());
		otherTutore = (Tutore) tor.findByCodeetu(trueForm.getCodeetu());
		if( (otherTuteur != null && otherTuteur.getId() != form.getId()) || otherTutore != null) {
			exception = true;
			errorMessage += "/codeetu";
		}
		
		if(exception) throw new DuplicateUniqueKeyException(errorMessage);
		
		
		
		
		Tuteur t = ot.get();
		
		byte[][] hash = hashPassword(trueForm.getPassword());
		
		
		t.setNom( trueForm.getNom() );
		t.setPrenom( trueForm.getPrenom() );
		t.setEmail( trueForm.getEmail() );
		t.setSalt(hash[0]);
		t.setPassword(hash[1]);
		t.setCodeetu( trueForm.getCodeetu() );
		t.setTelephone( trueForm.getTelephone() );
		t.setFiliere( trueForm.getFiliere() );
		
		
		tur.save(t);
		
		return this.getAllTuteurs();
		
	}
	
	public List<ProjectionTuteur> validationTuteur(ValidationTuteurToken token) throws NoElementException {
		
		
		//check :
		Optional<Tuteur> ot = tur.findById(token.getId());
		if(!ot.isPresent()) throw new NoElementException(token.getId());
		
		Tuteur t = ot.get();
		
		
		t.setValidationcompte(token.getValidation());
		tur.save(t);
		
		return this.getAllTuteurs();
		
	}
	
	public List<ProjectionTuteur> addCompetenceTuteur(MatiereToken token) throws NoElementException {
		
		//check :
		Optional<Tuteur> ot = tur.findById(token.getId());
		if(!ot.isPresent()) throw new NoElementException(token.getId());
		Tuteur t = ot.get();
		
		ArrayList<String> l = new ArrayList<String>(t.getDomaineDeCompetences());
		l.add(token.getMatiere());
		t.setDomaineDeCompetences(l);
		tur.save(t);
		
		return this.getAllTuteurs();
		
	}
	
	
	public List<ProjectionTuteur> removeCompetenceTuteur(MatiereToken token) throws NoElementException {
		
		//check :
		Optional<Tuteur> ot = tur.findById(token.getId());
		if(!ot.isPresent()) throw new NoElementException(token.getId());		
		
		
		Tuteur t = ot.get();
		ArrayList<String> l = new ArrayList<String>(t.getDomaineDeCompetences());
		l.remove(token.getMatiere());
		t.setDomaineDeCompetences(l);
		tur.save(t);
		
		return this.getAllTuteurs();
		
	}
	
	
	public List<ProjectionTuteur> removeTuteur(Long id) throws NoElementException{
		
		//check :
		Optional<Tuteur> ot = tur.findById(id);
		if(!ot.isPresent()) throw new NoElementException(id);
		
		tur.deleteById(id);
		return tur.findBy();
	}
	
	
	public List<ProjectionTuteur> getAllTuteurs(){
		return tur.findBy();
	}
	
	
	
	/*
	 *  Partie Tutore
	 */
	
	public List<ProjectionTutore> createTutore(EtudiantForm form) throws GeneralSecurityException, DuplicateUniqueKeyException {
		

		//check
		boolean exception = false;
		String errorMessage = "";
		
		Tuteur otherTuteur = (Tuteur) tur.findByEmail(form.getEmail());
		Tutore otherTutore = (Tutore) tor.findByEmail(form.getEmail());
		if(otherTuteur != null || otherTutore != null) {
			exception = true;
			errorMessage += "/email";
		}
				
		otherTuteur = (Tuteur) tur.findByCodeetu(form.getCodeetu());
		otherTutore = (Tutore) tor.findByCodeetu(form.getCodeetu());
		if(otherTuteur != null || otherTutore != null) {
			exception = true;
			errorMessage += "/codeetu";
		}
		
		
		if(exception) throw new DuplicateUniqueKeyException(errorMessage);
		
        byte[][] hash = hashPassword(form.getPassword());
        Tutore t = new Tutore(form.getNom(),form.getPrenom(),form.getEmail(),hash[1],hash[0],form.getCodeetu(),form.getTelephone(),form.getFiliere());
	    tor.save(t);
	        
	    return this.getAllTutores();

	}
	
	
	public List<ProjectionTutore> updateTutore(UpdateForm form) throws GeneralSecurityException, DuplicateUniqueKeyException, NoElementException {
		
		
		EtudiantForm trueForm = (EtudiantForm) form.getForm();
		
		
		//check
		Optional<Tutore> ot = tor.findById(form.getId());
		if(!ot.isPresent()) throw new NoElementException(form.getId());
		Tutore t = ot.get();
		
		
		boolean exception = false;
		String errorMessage = "";
		
		
		Tuteur otherTuteur = (Tuteur) tur.findByEmail(trueForm.getEmail());
		Tutore otherTutore = (Tutore) tor.findByEmail(trueForm.getEmail());
		if(otherTuteur != null || (otherTutore != null && otherTutore.getId() != form.getId())) {
			exception = true;
			errorMessage += "/email";
		}
				
		otherTuteur = (Tuteur) tur.findByCodeetu(trueForm.getCodeetu());
		otherTutore = (Tutore) tor.findByCodeetu(trueForm.getCodeetu());
		if(otherTuteur != null || (otherTutore != null && otherTutore.getId() != form.getId())) {
			exception = true;
			errorMessage += "/codeetu";
		}
		
		if(exception) throw new DuplicateUniqueKeyException(errorMessage);
		
		
		byte[][] hash = hashPassword(trueForm.getPassword());
		
		
		t.setNom( trueForm.getNom() );
		t.setPrenom( trueForm.getPrenom() );
		t.setEmail( trueForm.getEmail() );
		t.setSalt(hash[0]);
		t.setPassword(hash[1]);
		t.setCodeetu( trueForm.getCodeetu() );
		t.setTelephone( trueForm.getTelephone() );
		t.setFiliere( trueForm.getFiliere() );
		
		
		tor.save(t);
		
		return this.getAllTutores();
		
	}
	
	
	public List<ProjectionTutore> addDemandeTutore(MatiereToken token) throws NoSuchElementException, NoElementException {
		
		Optional<Tutore> ot = tor.findById(token.getId());
		if(!ot.isPresent()) throw new NoElementException(token.getId());
		Tutore t = ot.get();
		
		ArrayList<String> l = new ArrayList<String>(t.getDemandes());
		l.add(token.getMatiere());
		t.setDemandes(l);
		tor.save(t);
		
		return this.getAllTutores();
		
	}
	
	
	public List<ProjectionTutore> removeDemandeTutore(MatiereToken token) throws NoSuchElementException, NoElementException {
		
		Optional<Tutore> ot = tor.findById(token.getId());
		if(!ot.isPresent()) throw new NoElementException(token.getId());
		Tutore t = ot.get();
		
		
		ArrayList<String> l = new ArrayList<String>(t.getDemandes());
		l.remove(token.getMatiere());
		t.setDemandes(l);
		tor.save(t);
		
		return this.getAllTutores();
		
	}
	
	
	public List<ProjectionTutore> removeTutore(Long id) throws NoElementException{
		
		Optional<Tutore> ot = tor.findById(id);
		if(!ot.isPresent()) throw new NoElementException(id);
		
		tor.deleteById(id);
		return tor.findBy();
	}
	
	
	public List<ProjectionTutore> getAllTutores(){
		return tor.findBy();
	}
	
	
	
	
	
	
	
	public List<ProjectionSeance> getSeances(){
		return ser.findBy();
	}
	
	public void joinSeance(Long idTutore, Long idSeance) throws NoElementException, FullCourseException {
		
		Optional<Tutore> ot = tor.findById(idTutore);
		if(!ot.isPresent()) throw new NoElementException(idTutore);
		Tutore t = ot.get();
		
		Optional<Seance> os = ser.findById(idSeance);
		if(!os.isPresent()) throw new NoElementException(idSeance);
		Seance s = os.get();
		
		Set<Tutore> inscrits = s.getTutores();
		if(inscrits.size() >= s.getNbmaxtutores()) throw new FullCourseException(idSeance);
		
		inscrits.add(t);
		s.setTutores(inscrits);
		ser.save(s);
			
		
	}
	
	public List<Salle> getSalles(){
		return salr.findAll();
	}
	
}
