package app.etutorat.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Matiere {

	@Id
	private long id;
	
	private String nom;

	public Matiere() {}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
	
	
	
}
