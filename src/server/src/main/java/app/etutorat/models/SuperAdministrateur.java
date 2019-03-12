package app.etutorat.models;

import javax.persistence.Entity;

@Entity
public class SuperAdministrateur extends Utilisateur {

	
	
	public SuperAdministrateur() {}
	
	public SuperAdministrateur(
			String nom,
			String prenom,
			String email,
			byte[] password,
			byte[] salt) 
	{
		super(nom, prenom, email, password, salt);
	}
}
