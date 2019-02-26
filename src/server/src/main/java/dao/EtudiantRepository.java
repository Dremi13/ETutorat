package dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import models.Etudiant;

@Repository
public interface EtudiantRepository<T extends Serializable, K> extends UtilisateurRepository<Etudiant, Long> {

	public Etudiant findByCodeetu();
	public List<Etudiant> findByFiliere();
	
	
	
	
}
