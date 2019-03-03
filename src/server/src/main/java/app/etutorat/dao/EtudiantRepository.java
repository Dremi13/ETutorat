package app.etutorat.dao;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import app.etutorat.models.Etudiant;

@NoRepositoryBean
public interface EtudiantRepository extends UtilisateurRepository {

	public Etudiant findByCodeetu(String codeetu);
	public List<Etudiant> findByFiliere(String filiere);
	
	
	
	
}
