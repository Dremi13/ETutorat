package app.etutorat.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.etutorat.models.Tutore;
import app.etutorat.models.queryobjects.ProjectionTutore;

@Repository
public interface TutoreRepository extends JpaRepository<Tutore, Long>, EtudiantRepository {
	
	@Query("SELECT " +
	           "    d " +
	           "FROM " +
	           "    Tutore t " +
	           "JOIN " +
	           "	t.demandes d ")
	public List<String> findSesDemandes();

	public List<ProjectionTutore> findBy();

}
