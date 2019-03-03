package app.etutorat.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.etutorat.models.Tuteur;
import app.etutorat.models.queryobjects.PaireStringLong;

@Repository
public interface TuteurRepository extends JpaRepository<Tuteur, Long>, EtudiantRepository {

	//@Query a faire vite fait
	//public List<Tuteur> findByDomainedecompetences(String domaine);
	
	
	
	@Query("SELECT " +
	           "    new app.etutorat.models.queryobjects.PaireStringLong(d.nom, COUNT(t)) " +
	           "FROM " +
	           "    Tutore t " +
	           "JOIN " +
	           "	t.demandes d " +
	           "GROUP BY " +
	           "    d.id")
	public List<PaireStringLong> listDemandes();
	
}
