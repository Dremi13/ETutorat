package app.etutorat.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.etutorat.models.Salle;


@Repository
public interface SalleRepository extends JpaRepository<Salle, Long> {

	public List<Salle> findByNom(String nom);
	public List<Salle> findByCapaciteGreaterThanEqual(int x);
	public List<Salle> findByBatiment(String batiment);
	public List<Salle> findBySite(String site);
	
	
	@Query("SELECT s.batiment FROM Salle s")
	public List<String> listBatiments();
	
	
	@Query("SELECT s.site FROM Salle s")
	public List<String> listSites();
	
}
