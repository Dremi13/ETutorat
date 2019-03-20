package app.etutorat.models;



import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Etudiant extends Utilisateur {

	


	
	@Column(unique = true)
	private String codeetu;
	private String telephone;
	private String filiere;
	
	public Etudiant () {}
	
	public Etudiant(
			String nom,
			String prenom,
			String email,
			byte[] password,
			byte[] salt,
			String codeetu,
			String telephone,
			String filiere) 
	{
		super(nom, prenom, email, password, salt);
		this.codeetu = codeetu;
		this.telephone = telephone;
		this.filiere = filiere;
	}
	
	
	public String getCodeetu() {
		return codeetu;
	}
	public void setCodeetu(String codeEtu) {
		this.codeetu = codeEtu;
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


	
	
	
}
