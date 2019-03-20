package app.etutorat.models;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


@Entity
public class Seance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 0L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private OffsetDateTime dateDebut;
	private OffsetDateTime dateFin;
	private String outilAV;
	private String sujet;
	private int nbmaxtutores;
	
	
	@ManyToOne
	private Tuteur tuteur;
	
	
	@ManyToMany
	@JoinTable(	name = "inscription",
				joinColumns = @JoinColumn(name="SEANCE_ID", referencedColumnName="id"),
				inverseJoinColumns =  @JoinColumn(name="TUT_ID", referencedColumnName="id") )
	private Set<Tutore> tutores;
	
	
	
	@ManyToOne
	private Salle salle;
	

	public Seance() {}
	
	public Seance(
					OffsetDateTime dateDebut,
					OffsetDateTime dateFin,
					String outilAV,
					String sujet,
					int nbmaxtutores,
					Tuteur tuteur,
					Salle salle) {
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.outilAV = outilAV;
		this.sujet = sujet;
		this.nbmaxtutores = nbmaxtutores;
		this.tuteur = tuteur;
		this.salle = salle;
		this.tutores = new HashSet<Tutore>();
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	
	
	public int getNbmaxtutores() {
		return nbmaxtutores;
	}
	public void setNbmaxtutores(int nbmaxtutores) {
		this.nbmaxtutores = nbmaxtutores;
	}
	
	public Set<Tutore> getTutores() {
		return tutores;
	}
	public void setTutores(Set<Tutore> tutores) {
		this.tutores = tutores;
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
