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
