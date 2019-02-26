package dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import models.Administrateur;


@Repository
public interface AdminRepository extends JpaRepository<Administrateur, Long>{

}
