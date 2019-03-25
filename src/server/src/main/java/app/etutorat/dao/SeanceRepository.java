package app.etutorat.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.etutorat.models.Salle;
import app.etutorat.models.Seance;
import app.etutorat.models.Tuteur;
import app.etutorat.models.queryobjects.ProjectionSeance;

@Repository
public interface SeanceRepository extends JpaRepository<Seance, Long> {

	public List<ProjectionSeance> findBy();
	
	public List<Seance> findByOutilAV(String outilav);
	public List<Seance> findBySalle(Salle salle);
	public List<Seance> findByTuteur(Tuteur tuteur);
	
	//Sameshit que findByCompetences
	//public List<Seance> findByTutores(Tutore tutore);

	
	@Query("SELECT s " 				+
	           "FROM " 				+
	           "    Seance s " 		+
	           "JOIN " 				+
	           "	s.tutores t " 	+
	           "GROUP BY " 			+
	           "    s.id "			+
	           "HAVING " 			+
	           "	count(t) < s.nbmaxtutores")
	public List<Seance> findSeancesDisponibles();
}
