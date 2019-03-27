package app.etutorat.services;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.etutorat.dao.SalleRepository;
import app.etutorat.dao.SeanceRepository;
import app.etutorat.dao.TuteurRepository;
import app.etutorat.dao.TutoreRepository;
import app.etutorat.models.Salle;
import app.etutorat.models.Seance;
import app.etutorat.models.Tuteur;
import app.etutorat.models.Tutore;
import app.etutorat.models.queryobjects.ProjectionSeance;
import app.etutorat.models.queryobjects.ProjectionTuteur;
import app.etutorat.models.requestobjects.forms.SeanceForm;
import app.etutorat.models.requestobjects.forms.UpdateForm;
import app.exceptions.FullCourseException;
import app.exceptions.NoElementException;
import app.exceptions.SeanceCollisionException;
import app.exceptions.TooManyHoursException;

@Service
@Transactional
public class SeanceService {

	@Autowired
	private SeanceRepository ser;
	
	@Autowired
	private SalleRepository salr;
	
	@Autowired
	private TuteurRepository tur;
	
	@Autowired
	private TutoreRepository tor;
	
	

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
		checkTuteurHours(s.getTuteur(),s.getDateDebut().until(s.getDateFin(), ChronoUnit.MINUTES),null);
		
		
		return ser.save(s);
				
		
	}
	
	public void joinSeance(Long idSeance, Long idTutore) throws NoElementException, FullCourseException {
		
		Optional<Tutore> ot = tor.findById(idTutore);
		if(!ot.isPresent()) throw new NoElementException(idTutore);
		Tutore t = ot.get();
		
		Optional<Seance> os = ser.findById(idSeance);
		if(!os.isPresent()) throw new NoElementException(idSeance);
		Seance s = os.get();
		
		List<Tutore> inscrits = s.getTutores();
		if(inscrits.size() >= s.getNbmaxtutores()) throw new FullCourseException(idSeance);
		if(inscrits.contains(t)) return; //should throw an exception but i don't have the time i guess. (hard to be alone).
		
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
		if(s.getTuteur().getId() != sf.getTuteur().getId()) checkTuteurHours(sf.getTuteur(),sf.getDateDebut().until(sf.getDateFin(), ChronoUnit.MINUTES),null);
		else 												checkTuteurHours(sf.getTuteur(),sf.getDateDebut().until(sf.getDateFin(), ChronoUnit.MINUTES),s);
		
		s.setDateDebut(sf.getDateDebut());
		s.setDateFin(sf.getDateFin());
		s.setNbmaxtutores(sf.getNbmaxtutores());
		s.setSalle(sf.getSalle());
		s.setOutilAV(sf.getOutilAV());
		s.setTuteur(sf.getTuteur());
		
		ser.save(s);
		
		
	}
	
	
	public void removeSeance(Long idSeance, Long idTuteur) throws NoElementException {
		
		Optional<Seance> os = ser.findById(idSeance);
		if(!os.isPresent()) throw new NoElementException(idSeance);
		Seance s = os.get();
		
		if(s.getTuteur().getId() != idTuteur) return;
		
		ser.delete(s);
		
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
	//previousSeance is used for update with tuteur change. null otherwise.
	public void checkTuteurHours(Tuteur tuteur, long nextSeanceDuration, Seance previousSeance) throws TooManyHoursException {
		List<Seance> seances = ser.findByTuteur(tuteur);
		long time = 0;
		
		//Update without tuteur change : don't count the previous seance.
		if(previousSeance != null) {
			for(Seance s : seances) {
				if(s.getId() != previousSeance.getId()) time += s.getDateDebut().until(s.getDateFin(), ChronoUnit.MINUTES);
			}
		}
		
		//Create/Update with tuteur change : no need to omit a previous seance.
		else {
			for(Seance s : seances) {
				time += s.getDateDebut().until(s.getDateFin(), ChronoUnit.MINUTES);
			}
		}
		
		//23 h =  1380 minutes
		if(time + nextSeanceDuration > 1380) throw new TooManyHoursException(tuteur,time);
	}
	
	
	public List<Salle> getSalles(){
		return salr.findAll();
	}
	
	
	public ProjectionTuteur getCurrentTuteur(Long id) {
		return tur.findProjectionById(id).get();
	}
}
