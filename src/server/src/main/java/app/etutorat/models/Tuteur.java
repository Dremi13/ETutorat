package app.etutorat.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Tuteur extends Etudiant {

	
	
	private static final long serialVersionUID = 2L;
	

	@ManyToMany
	@JoinTable(
			   name="competences",
			   joinColumns=@JoinColumn(name="TUTEUR_ID", referencedColumnName="id"),
			   inverseJoinColumns=@JoinColumn(name="MAT_ID", referencedColumnName="id"))
	private Set<Matiere> domaineDeCompetences;
	
	
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
			Set<Matiere> domaineDeCompetences, 
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
		
		
	}
	
	
	public Set<Matiere> getDomaineDeCompetences() {
		return domaineDeCompetences;
	}
	public void setDomaineDeCompetences(Set<Matiere> domaineDeCompetences) {
		this.domaineDeCompetences = domaineDeCompetences;
	}
	
	public boolean isValidationCompte() {
		return validationcompte;
	}
	public void setValidationCompte(boolean validationCompte) {
		this.validationcompte = validationCompte;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
