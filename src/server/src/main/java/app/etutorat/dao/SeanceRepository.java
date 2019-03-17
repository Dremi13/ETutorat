package app.etutorat.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	           "Seance s  join fetch s.tutores c where c.id  =:id"
	        	)
	public List<Seance> findByIdTutore( @Param("id")  Long id);
	
	
	
	
	@Query("SELECT s " 					+
	           "FROM " 					+
	           "    Seance s where s.tuteur.id = :id ")                 
	public List<Seance> findByIdTuteur(@Param("id")  Long id);
	
	
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
	
	@Query("SELECT s " 				+
	           "FROM " 				+
	           "    Seance s ")
	public List<Seance> findAllSeances();
	
}
