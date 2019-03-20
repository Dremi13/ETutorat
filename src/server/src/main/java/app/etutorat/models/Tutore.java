package app.etutorat.models;


import java.io.Serializable;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;



@Entity
public class Tutore extends Etudiant implements Serializable {

	
	
	private static final long serialVersionUID = 3L;
	
	
	/*@ManyToMany
	@JoinTable(
			   name="Demande",
			   joinColumns=@JoinColumn(name="TUTORE_ID", referencedColumnName="id"),
			   inverseJoinColumns=@JoinColumn(name="MAT_ID", referencedColumnName="id"))
	private Set<Matiere> demandes;*/
	
	@ElementCollection
	private List<String> demandes;
	
	
	

	public Tutore() {}
	
	public Tutore(
			String nom,
			String prenom,
			String email,
			byte[] password,
			byte[] salt,
			String codeetu,
			String telephone,
			String filiere) 
	{
		super(nom, prenom, email, password, salt, codeetu, telephone, filiere);
		this.demandes = null;
	}

	
	 
	public List<String> getDemandes() {
		return demandes;
	}

	public void setDemandes(List<String> demandes) {
		this.demandes = demandes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	} 
}
