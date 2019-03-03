package app.etutorat.dao;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

import app.etutorat.models.Utilisateur;


@NoRepositoryBean
public interface UtilisateurRepository {

	
	public List<Utilisateur> findByNom(String nom);
	public Utilisateur findByEmail(String email);
	
}
