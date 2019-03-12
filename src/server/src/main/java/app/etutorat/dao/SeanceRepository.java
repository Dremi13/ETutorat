package app.etutorat.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.etutorat.models.Salle;
import app.etutorat.models.Seance;
import app.etutorat.models.Tuteur;
import app.etutorat.models.Tutore;

@Repository
public interface SeanceRepository extends JpaRepository<Seance, Long> {

	
	
	public List<Seance> findByDate(Date date);
	public List<Seance> findByOutilAV(String outilav);
	public List<Seance> findBySalle(Salle salle);
	@Query("SELECT s " 					+
	           "FROM " 					+
	           "    Seance s " 			+
	           "WHEN" 					+
	           "s.tuteur like  tuteur " +
	           "GROUP BY " 				+
	           "    s.id "			)
	public List<Seance> findByTuteur(Tuteur tuteur);
	
	//Sameshit que findByCompetences
	@Query("SELECT s " 					+
	           "FROM " 					+
	           "    Seance s " 			+
	           "WHEN" 					+
	           "s.tutore like  tutore " +
	           "GROUP BY " 				+
	           "    s.id "			)
	public List<Seance> findByTutores(Tutore tutore);

	
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
