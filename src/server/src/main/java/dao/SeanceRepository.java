package dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import models.Seance;

@Repository
public interface SeanceRepository extends JpaRepository<Seance, Long> {

	
	
	public List<Seance> findByDate();
	public List<Seance> findByOutilav();
	public List<Seance> findBySalle();
	public List<Seance> findByTuteur();
	public List<Seance> findByTutore();

	
	@Query("SELECT s " 				+
	           "FROM " 				+
	           "    Seance s " 		+
	           "JOIN " 				+
	           "	s.tutores t " 	+
	           "GROUP BY " 			+
	           "    s.id"			+
	           "HAVING " 			+
	           "	count(t) < s.nbmaxtutores")
	public List<Seance> findSeancesDisponibles();
}
