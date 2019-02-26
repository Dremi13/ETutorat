package models;


import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;



@Entity
public class Tutore extends Etudiant {

	
	
	private static final long serialVersionUID = 3L;
	
	
	@ManyToMany
	@JoinTable(
			   name="Demande",
			   joinColumns=@JoinColumn(name="TUT_ID", referencedColumnName="id"),
			   inverseJoinColumns=@JoinColumn(name="MAT_ID", referencedColumnName="id"))
	private Set<Matiere> demandes;

	public Set<Matiere> getDemandes() {
		return demandes;
	}

	public void setDemandes(Set<Matiere> demandes) {
		this.demandes = demandes;
	}
	 
	public static long getSerialversionuid() {
		return serialVersionUID;
	} 
}
