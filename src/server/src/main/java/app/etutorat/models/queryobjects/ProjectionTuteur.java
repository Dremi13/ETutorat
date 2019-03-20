package app.etutorat.models.queryobjects;

import java.util.List;

public interface ProjectionTuteur {

	
	
	Long getId();
	String getNom();
	String getPrenom();
	String getEmail();
	String getCodeetu();
	String getFiliere();
	String getTelephone();
	boolean getValidationcompte();
	List<String> getDomaineDeCompetences();
	
}
