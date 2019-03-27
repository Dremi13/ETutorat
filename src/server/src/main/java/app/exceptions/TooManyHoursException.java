package app.exceptions;

import app.etutorat.models.Tuteur;

public class TooManyHoursException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7056755276657891314L;
	
	
	public TooManyHoursException(Tuteur tuteur, long time) {
		super("The tuteur " + tuteur.getPrenom() + " " + tuteur.getNom() +" (id:" + tuteur.getId() + ") will exceed its 23h limitations ! Currently, without this course, "+time/60 +"hours " + time%60 + "minutes");
	}

}
