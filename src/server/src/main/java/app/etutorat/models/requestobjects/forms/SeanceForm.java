package app.etutorat.models.requestobjects.forms;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;


import app.etutorat.models.Salle;
import app.etutorat.models.Tuteur;
import app.etutorat.models.requestobjects.Form;
import app.exceptions.formException.BadSeanceFormException;

public class SeanceForm implements Form, Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4300231772151740160L;
	
	
	private OffsetDateTime dateDebut;
	private OffsetDateTime dateFin;
	private String outilAV;
	private String sujet;
	private Integer nbmaxtutores;
	private Tuteur tuteur;
	private Salle salle;
	
	public SeanceForm() {}
	
	public SeanceForm(OffsetDateTime dateDebut, OffsetDateTime dateFin, String outilAV, String sujet, int nbmaxtutores, Tuteur tuteur,Salle salle) {
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.outilAV = outilAV;
		this.sujet = sujet;
		this.nbmaxtutores = nbmaxtutores;
		this.tuteur = tuteur;
		this.salle = salle;
	}
	
	
	
	@Override
	public void isValid() throws BadSeanceFormException {
		boolean res = true;
		String message = "";
		
		if(this.getDateDebut() == null) 														{ res = false; message += " start required"; 			}
		if(this.getDateFin() == null) 															{ res = false; message += " end required";		}
		if(this.getOutilAV() == null) 															{ res = false; message += " outilAV required"; 		}
		if(this.getSujet() == null || this.getSujet().equals("")) 								{ res = false; message += " sujet required"; 	}
		if(this.getNbmaxtutores() == null) 														{ res = false; message += " nbmaxtutores required"; 	}
		if(this.getTuteur() == null ) 															{ res = false; message += " tuteur required"; 	}
		if(this.getSalle() == null) 															{ res = false; message += " salle required"; 	}
		
		
		if(this.getDateDebut().isAfter(this.getDateFin())) 										{ res = false; message += " the end should be after the start"; 	}
		if(this.getDateDebut().until(this.getDateFin(), ChronoUnit.HOURS) > 2)					{ res = false; message += " the course shouldn't last more than 2 hours"; 	}
		//GMT+1 client side but UTC server side, so -1h.
		if(this.getDateDebut().getHour() < 7 || this.getDateDebut().getHour() > 19 || this.getDateFin().getHour() < 7 || this.getDateFin().getHour() > 19)				{ res = false; message += " the course should respect the hours (8h-20h GMT+1)"; 	}
		if(this.getNbmaxtutores() > this.getSalle().getCapacite())								{ res = false; message += " nbmaxtutores can't exceed the room capacity"; 	}
		
		if(!res) throw new BadSeanceFormException(message);

	}
	
	
	public OffsetDateTime getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(OffsetDateTime dateDebut) {
		this.dateDebut = dateDebut;
	}


	public OffsetDateTime getDateFin() {
		return dateFin;
	}
	public void setDateFin(OffsetDateTime dateFin) {
		this.dateFin = dateFin;
	}


	public String getOutilAV() {
		return outilAV;
	}
	public void setOutilAV(String outilAV) {
		this.outilAV = outilAV;
	}



	public String getSujet() {
		return sujet;
	}
	public void setSujet(String sujet) {
		this.sujet = sujet;
	}


	public Integer getNbmaxtutores() {
		return nbmaxtutores;
	}
	public void setNbmaxtutores(int nbmaxtutores) {
		this.nbmaxtutores = nbmaxtutores;
	}


	public Tuteur getTuteur() {
		return tuteur;
	}
	public void setTuteur(Tuteur tuteur) {
		this.tuteur = tuteur;
	}



	public Salle getSalle() {
		return salle;
	}
	public void setSalle(Salle salle) {
		this.salle = salle;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	

}
