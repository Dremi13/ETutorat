package app.etutorat.services;

import java.util.List;

import app.etutorat.models.Tuteur;
import app.etutorat.models.Tutore;
import app.etutorat.models.Utilisateur;

public interface AdminService {

		 void addTuteur(Tuteur tuteur);   //ajouter tuteur
		 void deleteTuteur(Long idTuteur); //suppr tuteur
		 public List<Tuteur> getTuteur();
		 
		 void addTutore (Tutore tutore); //ajouter tutore
		 void deleteTutore(long idTutore); //supp tutore
		 public List<Tutore> getTutore();
		
		 List<Utilisateur> getAllUtilisateur();  // retourne liste utilistateur
		 
		 void validAndUpdateTuteur(Tuteur tuteur); // valider un tuteur
		 void rejectTuteur(Tuteur tuteur);  // bloquer un tuteur
		 
		 
		

		
}