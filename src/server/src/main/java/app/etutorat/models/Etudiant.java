package app.etutorat.models;


import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Etudiant extends Utilisateur {

	

	
	@Column(unique = true)
	private String codeetu;
	private int telephone;
	private String filiere;
	
	
	
	
	public String getCodeetu() {
		return codeetu;
	}
	public void setCodeetu(String codeEtu) {
		this.codeetu = codeEtu;
	}
	public int getTelephone() {
		return telephone;
	}
	public void setTelephone(int telephone) {
		this.telephone = telephone;
	}

	public String getFiliere() {
		return filiere;
	}
	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}
	
	
	
}
