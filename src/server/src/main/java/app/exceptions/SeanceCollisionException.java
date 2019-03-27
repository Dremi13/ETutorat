package app.exceptions;

public class SeanceCollisionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4299261714628919109L;

	public SeanceCollisionException(Long idSeance) {
		super("Collision between the new seance and the seance "+idSeance);
	}
	
}
