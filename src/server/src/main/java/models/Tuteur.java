package models;

import javax.persistence.Entity;

@Entity
public class Tuteur extends Etudiant {

	
	
	private static final long serialVersionUID = 2L;
	

	private String specialite;
	private boolean validationCompte;
	
	
	
	
	public String getSpecialite() {
		return specialite;
	}
	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}
	public boolean isValidationCompte() {
		return validationCompte;
	}
	public void setValidationCompte(boolean validationCompte) {
		this.validationCompte = validationCompte;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
