package dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dao.queryobjects.PaireStringInt;
import models.Tuteur;

@Repository
public interface TuteurRepository extends EtudiantRepository<Tuteur, Long> {

	
	public List<Tuteur> findBySpecialite();
	
	
	
	@Query("SELECT " +
	           "    new PaireStringInt(d.nom, COUNT(t)) " +
	           "FROM " +
	           "    Tutore t " +
	           "JOIN " +
	           "	t.demandes d " +
	           "GROUP BY " +
	           "    d.id")
	public List<PaireStringInt> listDemandes();
	
}
