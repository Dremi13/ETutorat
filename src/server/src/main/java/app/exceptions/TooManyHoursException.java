package app.exceptions;

public class TooManyHoursException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7056755276657891314L;
	
	
	public TooManyHoursException(Long tuteurId, long time) {
		super("This tuteur " + tuteurId + "will exceed its 23h limitations ! Actually "+time/60+" hours and "+time%60+" minutes");
	}

}
