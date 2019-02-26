package models;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;



@Entity
public class Seance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 0L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Date date;
	private String outilAV;
	private String sujet;
	private int nbMaxTutores;
	
	
	@OneToMany
	private Tuteur tuteur;
	
	
	@ManyToMany
	@JoinTable(	name = "inscription",
				joinColumns = @JoinColumn(name="SEANCE_ID", referencedColumnName="id"),
				inverseJoinColumns =  @JoinColumn(name="TUT_ID", referencedColumnName="id") )
	private Set<Tutore> tutores;
	
	
	
	
	@OneToMany
	private Salle salle;
	

	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	
	public int getNbMaxTutores() {
		return nbMaxTutores;
	}
	public void setNbMaxTutores(int nbMaxTutores) {
		this.nbMaxTutores = nbMaxTutores;
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
