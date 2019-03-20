package app.etutorat.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.etutorat.models.Administrateur;
import app.etutorat.models.queryobjects.ProjectionAdmin;


@Repository
public interface AdminRepository extends JpaRepository<Administrateur, Long>, UtilisateurRepository{

	
	List<ProjectionAdmin> findBy();
}
