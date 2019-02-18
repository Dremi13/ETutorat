package models;


import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Etudiant extends Utilisateur {

	private String codeEtu;
	private int telephone;
	private String filière;
	
	public String getCodeEtu() {
		return codeEtu;
	}
	public void setCodeEtu(String codeEtu) {
		this.codeEtu = codeEtu;
	}
	public int getTelephone() {
		return telephone;
	}
	public void setTelephone(int telephone) {
		this.telephone = telephone;
	}
	public String getFilière() {
		return filière;
	}
	public void setFilière(String filière) {
		this.filière = filière;
	}
	
	
	
}
