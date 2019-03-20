package app.etutorat.models;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class SuperAdministrateur extends Utilisateur  implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1827957221873651153L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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
