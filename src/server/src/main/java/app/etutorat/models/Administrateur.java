package app.etutorat.models;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
public class Administrateur extends Utilisateur {

	
	
	public Administrateur() {}
	
	public Administrateur(
			String nom,
			String prenom,
			String email,
			byte[] password,
			byte[] salt) 
	{
		super(nom, prenom, email, password, salt);
	}
	
}
