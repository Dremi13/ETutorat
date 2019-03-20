package app.etutorat.models;


import java.io.Serializable;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class Tuteur extends Etudiant implements Serializable {

	
	
	private static final long serialVersionUID = 2L;
	

	/*@ManyToMany
	@JoinTable(
			   name="competences",
			   joinColumns=@JoinColumn(name="TUTEUR_ID", referencedColumnName="id"),
			   inverseJoinColumns=@JoinColumn(name="MAT_ID", referencedColumnName="id"))
	private Set<Matiere> domaineDeCompetences;*/
	
	@ElementCollection
	private List<String> domaineDeCompetences;
	
	
	private boolean validationcompte;
	
	
	public Tuteur() {}
	
	
	public Tuteur(
			String nom,
			String prenom,
			String email,
			byte[] password,
			byte[] salt,
			String codeetu,
			String telephone,
			String filiere,
			List<String> domaineDeCompetences, 
			boolean validationcompte) 
	{
		super(nom, prenom, email, password, salt, codeetu, telephone, filiere);
		this.domaineDeCompetences = domaineDeCompetences;
		this.validationcompte = validationcompte;
		
		
	}
	
	public Tuteur(
			String nom,
			String prenom,
			String email,
			byte[] password,
			byte[] salt,
			String codeetu,
			String telephone,
			String filiere,
			boolean validationcompte) 
	{
		super(nom, prenom, email, password, salt, codeetu, telephone, filiere);
		this.domaineDeCompetences = null;
		this.validationcompte = validationcompte;
		
		
	}
	
	public Tuteur(
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
		this.domaineDeCompetences = null;
		this.validationcompte = false;
		
	}
	
	
	
	
	public List<String> getDomaineDeCompetences() {
		return domaineDeCompetences;
	}


	public void setDomaineDeCompetences(List<String> domaineDeCompetences) {
		this.domaineDeCompetences = domaineDeCompetences;
	}


	public boolean getValidationcompte() {
		return validationcompte;
	}


	public void setValidationcompte(boolean validationcompte) {
		this.validationcompte = validationcompte;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
