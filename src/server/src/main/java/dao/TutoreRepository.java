package dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import models.Tutore;

@Repository
public interface TutoreRepository extends EtudiantRepository<Tutore, Long> {
	
	@Query("SELECT " +
	           "    new String(d.nom) " +
	           "FROM " +
	           "    Tutore t " +
	           "JOIN " +
	           "	t.demandes d ")
	public List<String> findSesDemandes();

}
