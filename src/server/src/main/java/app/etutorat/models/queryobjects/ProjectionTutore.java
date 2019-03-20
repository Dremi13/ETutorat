package app.etutorat.models.queryobjects;

import java.util.List;

public interface ProjectionTutore {

	Long getId();
	String getNom();
	String getPrenom();
	String getEmail();
	String getCodeetu();
	String getFiliere();
	String getTelephone();
	List<String> getDemandes();
	
	
}
