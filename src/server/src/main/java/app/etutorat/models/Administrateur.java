package app.etutorat.models;

import java.io.Serializable;

import javax.persistence.Entity;
@Entity
public class Administrateur extends Utilisateur  implements Serializable {

	
	


	/**
	 * 
	 */
	private static final long serialVersionUID = -314804994790507035L;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
