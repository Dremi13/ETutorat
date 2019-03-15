package app.etutorat.services;

import java.util.List;


import app.etutorat.models.Tuteur;
import app.etutorat.models.Tutore;
public interface AdminService {

		 void addTuteur(Tuteur tuteur);   //ajouter tuteur
		 void deleteTuteur(Long idTuteur); //suppr tuteur
		 
		 void addTutore (Tutore tutore); //ajouter tutore
		 void deleteTutore(long idTutore); //supp tutore
		
		 
		 void validAndUpdateTuteur(Tuteur tuteur); // valider un tuteur
		 void rejectTuteur(Tuteur tuteur);  // bloquer un tuteur
		 
		 List<Tuteur> findAllTuteurs();  //listeTuteurs
		 List<Tutore> findAllTutores(); //listeTutores
		 
		 
		

		
}