package app.etutorat.services;

import java.security.GeneralSecurityException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
import app.etutorat.models.requestobjects.forms.SeanceForm;
import app.etutorat.models.requestobjects.forms.UpdateForm;
import app.exceptions.DuplicateUniqueKeyException;
import app.exceptions.FullCourseException;
import app.exceptions.NoElementException;
import app.exceptions.SeanceCollisionException;
import app.exceptions.TooManyHoursException;

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
	
	public Seance createSeance(SeanceForm form) throws SeanceCollisionException, TooManyHoursException {
		
		Seance s = new Seance(	form.getDateDebut(),
								form.getDateFin(),
								form.getOutilAV(),
								form.getSujet(),
								form.getNbmaxtutores(),
								form.getTuteur(),
								form.getSalle());
		//Check collision
		checkCollision(s,true);
		
		//Verify the number of hours of the new Tuteur
		checkTuteurHours(s.getTuteur(),s.getDateDebut().until(s.getDateFin(), ChronoUnit.MINUTES));
		
		
		return ser.save(s);
				
		
	}
	
	public void joinSeance(Long idTutore, Long idSeance) throws NoElementException, FullCourseException {
		
		Optional<Tutore> ot = tor.findById(idTutore);
		if(!ot.isPresent()) throw new NoElementException(idTutore);
		Tutore t = ot.get();
		
		Optional<Seance> os = ser.findById(idSeance);
		if(!os.isPresent()) throw new NoElementException(idSeance);
		Seance s = os.get();
		
		List<Tutore> inscrits = s.getTutores();
		if(inscrits.size() >= s.getNbmaxtutores()) throw new FullCourseException(idSeance);
		
		inscrits.add(t);
		s.setTutores(inscrits);
		ser.save(s);
			
		
	}
	
	
	
	public void updateSeance(UpdateForm form) throws NoElementException, SeanceCollisionException, TooManyHoursException {
		SeanceForm sf = (SeanceForm) form.getForm();
		Optional<Seance> os = ser.findById(form.getId());
		if(!os.isPresent()) throw new NoElementException(form.getId());
		Seance s = os.get();
		
		//Collision check
		checkCollision(s, false);
		
		//Verify the number of hours of the new Tuteur
		if(s.getTuteur().getId() != sf.getTuteur().getId()) {
			checkTuteurHours(sf.getTuteur(),sf.getDateDebut().until(sf.getDateFin(), ChronoUnit.MINUTES));
		}
		
		s.setDateDebut(sf.getDateDebut());
		s.setDateFin(sf.getDateFin());
		s.setNbmaxtutores(sf.getNbmaxtutores());
		s.setSalle(sf.getSalle());
		s.setOutilAV(sf.getOutilAV());
		s.setTuteur(sf.getTuteur());
		
		ser.save(s);
		
		
	}
	
	//create = true if s is a new seance, false if it's an update. Use to insure s doesn't collide with itself.
	//Throw SeanceCollisionException if there is collision.
	public void checkCollision(Seance s, boolean create) throws SeanceCollisionException {
		if(s.getOutilAV().equals("")) {
			for(Seance other : ser.findBySalle(s.getSalle())) {
				if(!create && s.getId() != other.getId()) {
					if( ((s.getDateDebut().compareTo(other.getDateDebut()) >= 0)   && s.getDateDebut().compareTo(other.getDateFin()) < 0 ) || (s.getDateFin().compareTo(other.getDateFin()) <= 0  && s.getDateFin().compareTo(other.getDateDebut()) > 0) || (s.getDateDebut().compareTo(other.getDateDebut()) <= 0 && s.getDateFin().compareTo(other.getDateFin()) >= 0) ) {
						throw new SeanceCollisionException(other.getId());
					}
				}
			}
		}
	}
	
	
	//Throw TooManyHoursException if the tuteur will exceed his time limitation (23h for now).
	public void checkTuteurHours(Tuteur tuteur, long nextSeanceDuration) throws TooManyHoursException {
		List<Seance> seances = ser.findByTuteur(tuteur);
		long time = 0;
		
		for(Seance s : seances) {
			time += s.getDateDebut().until(s.getDateFin(), ChronoUnit.MINUTES);
		}
		
		//23 h =  1380 minutes
		if(time + nextSeanceDuration > 1380) throw new TooManyHoursException(tuteur.getId(),time);
	}
	
	
	public List<Salle> getSalles(){
		return salr.findAll();
	}
	
}
