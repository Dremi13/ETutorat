package dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import models.Salle;


@Repository
public interface SalleRepository extends JpaRepository<Salle, Long> {

	public List<Salle> findByNom();
	public List<Salle> findByCapaciteGreaterThanEqual();
	public List<Salle> findByBatiment();
	public List<Salle> findBySite();
	
	
	@Query("SELECT new String(s.batiment) FROM Salle s")
	public List<String> listBatiments();
	
	
	@Query("SELECT new String(s.site) FROM Salle s")
	public List<String> listSites();
	
}
