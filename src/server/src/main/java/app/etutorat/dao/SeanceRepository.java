package app.etutorat.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.etutorat.models.Salle;
import app.etutorat.models.Seance;
import app.etutorat.models.Tuteur;

@Repository
public interface SeanceRepository extends JpaRepository<Seance, Long> {

	
	
	public List<Seance> findByDate(Date date);
	public List<Seance> findByOutilAV(String outilav);
	public List<Seance> findBySalle(Salle salle);
	public List<Seance> findByTuteur(Tuteur tuteur);
	
	//Sameshit que findByCompetences
	//public List<Seance> findByTutores(Tutore tutore);

	@Query("SELECT s.sujet " 					+
	           "FROM " 					+
	           "    Seance s"
	        	)
	public List<Seance> findByIdTutore(Tutore tutore);
	
	@Query("SELECT s " 					+
	           "FROM " 					+
	           "    Seance s where s.tuteur = :tuteur ")                 
	public List<Seance> findByIdTuteur(Tuteur tuteur);
	
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
