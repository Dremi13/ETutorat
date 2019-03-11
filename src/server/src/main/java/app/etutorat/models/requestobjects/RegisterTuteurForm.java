package app.etutorat.models.requestobjects;

import java.io.Serializable;

public class RegisterTuteurForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8791581286835103100L;

	private String nom;
	private String prenom;
	private String email;
	private String password;
	private String codeetu;
	private String telephone;
	private String filiere;
	
	public RegisterTuteurForm() {}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getCodeetu() {
		return codeetu;
	}
	public void setCodeetu(String codeetu) {
		this.codeetu = codeetu;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getFiliere() {
		return filiere;
	}
	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
