package app.exceptions;

public class DuplicateUniqueKeyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4247872306955978518L;

	
	public DuplicateUniqueKeyException(String keyType) {
		super(keyType);
	}
}
