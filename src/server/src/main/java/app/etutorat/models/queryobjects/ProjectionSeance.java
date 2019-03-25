package app.etutorat.models.queryobjects;

import java.time.OffsetDateTime;
import java.util.Set;

import app.etutorat.models.Salle;

public interface ProjectionSeance {

	Long getId();
	
	OffsetDateTime getDateDebut();
	OffsetDateTime getDateFin();
	String getOutilAV();
	String getSujet();
	int getNbmaxtutores();
	ProjectionTuteur getTuteur();
	Set<ProjectionTutore> getTutores();
	Salle getSalle();
}
