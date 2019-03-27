package app.etutorat.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.etutorat.models.Tuteur;
import app.etutorat.models.queryobjects.PaireStringLong;
import app.etutorat.models.queryobjects.ProjectionTuteur;

@Repository
public interface TuteurRepository extends JpaRepository<Tuteur, Long>, EtudiantRepository {

	
	//public List<Tuteur> findByDomainedecompetences(String domaine);
	
	
	
	@Query("SELECT " +
	           "    new app.etutorat.models.queryobjects.PaireStringLong(d, COUNT(t)) " +
	           "FROM " +
	           "    Tutore t " +
	           "JOIN " +
	           "	t.demandes d " +
	           "GROUP BY " +
	           "    d.id")
	public List<PaireStringLong> listDemandes();

	
	public List<ProjectionTuteur> findBy();
	public Optional<ProjectionTuteur> findProjectionById(Long id);
	
}
