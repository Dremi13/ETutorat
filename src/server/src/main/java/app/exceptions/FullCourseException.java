package app.exceptions;

public class FullCourseException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4247872306955978518L;

	
	public FullCourseException(Long id) {
		super("Already full course "+id);
	}

}
