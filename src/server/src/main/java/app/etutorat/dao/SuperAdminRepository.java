package app.etutorat.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import app.etutorat.models.SuperAdministrateur;

public interface SuperAdminRepository extends JpaRepository<SuperAdministrateur, Long>, UtilisateurRepository {

}
