package app.etutorat.models.requestobjects.forms;

import java.io.Serializable;

import app.etutorat.models.requestobjects.Form;
import app.exceptions.formException.BadCreateAdminFormException;

public class CreateAdminForm implements Serializable, Form {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4537478655621575590L;
	
	private String nom;
	private String prenom;
	private String email;
	private String password;
	
	public CreateAdminForm() {}
	
	public CreateAdminForm(String nom, String prenom, String email, String password) {
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.password = password;
	}
	
	public void isValid() throws BadCreateAdminFormException {
		
		boolean res = true;
		String message = "";
		
		if(this.getNom() == null || this.getNom().equals("")) 							{ res = false; message += " nom required"; 			}
		if(this.getPrenom() == null || this.getPrenom().equals("")) 					{ res = false; message += " prenom required";		}
		if(this.getEmail() == null || this.getEmail().equals("")) 						{ res = false; message += " email required"; 		}
		if(this.getPassword() == null || this.getPassword().equals("")) 				{ res = false; message += " password required"; 	}
		if(this.getPassword().length() < 4 || this.getPassword().length() > 16) 		{ res = false; message += " wrong password size"; 	}
		
		
		if(!res) throw new BadCreateAdminFormException(message);
		
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
