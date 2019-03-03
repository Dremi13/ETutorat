package app.etutorat.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.etutorat.models.Tutore;

@Repository
public interface TutoreRepository extends JpaRepository<Tutore, Long>, EtudiantRepository {
	
	@Query("SELECT " +
	           "    d.nom " +
	           "FROM " +
	           "    Tutore t " +
	           "JOIN " +
	           "	t.demandes d ")
	public List<String> findSesDemandes();

}
