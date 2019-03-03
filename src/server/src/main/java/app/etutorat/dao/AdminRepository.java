package app.etutorat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.etutorat.models.Administrateur;


@Repository
public interface AdminRepository extends JpaRepository<Administrateur, Long>, UtilisateurRepository{

}
