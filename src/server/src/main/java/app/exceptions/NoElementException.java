package app.exceptions;

public class NoElementException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -681637967069279336L;

	public NoElementException(Long id) {
		super("No element for id "+id.toString());
	}
	
}
