package models;

public class Tuteur extends Etudiant {

	
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
	
	
}
