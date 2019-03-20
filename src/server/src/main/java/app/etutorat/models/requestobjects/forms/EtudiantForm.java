package app.etutorat.models.requestobjects.forms;

import java.io.Serializable;

import app.etutorat.models.requestobjects.Form;
import app.exceptions.formException.BadRegisterFormException;

public class EtudiantForm implements Serializable, Form {

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
	
	

	public EtudiantForm() {}
	
	public EtudiantForm(String nom, String prenom, String email, String password, String codeetu, String telephone, String filiere) {
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.password = password;
		this.codeetu = codeetu;
		this.telephone = telephone;
		this.filiere = filiere;
	}
	

	
	public void isValid() throws BadRegisterFormException {
		
		boolean res = true;
		String message = "";
		
		if(this.getNom() == null || this.getNom().equals("")) 									{ res = false; message += " nom required"; 			}
		if(this.getPrenom() == null || this.getPrenom().equals("")) 							{ res = false; message += " prenom required";		}
		if(this.getEmail() == null|| this.getEmail().equals("")) 								{ res = false; message += " email required"; 		}
		if(this.getPassword() == null || this.getPassword().equals("")) 						{ res = false; message += " password required"; 	}
		if(this.getCodeetu() == null || this.getCodeetu().equals("")) 							{ res = false; message += " student code required"; 	}
		if(this.getTelephone() == null || this.getTelephone().equals("")) 						{ res = false; message += " telephone number required"; 	}
		if(this.getFiliere() == null || this.getFiliere().equals("")) 							{ res = false; message += " filiere required"; 	}
		
		if(this.getPassword().length() < 4 || this.getPassword().length() > 16) 				{ res = false; message += " wrong password size"; 	}
		
		if(!res) throw new BadRegisterFormException(message);
	}
	
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
