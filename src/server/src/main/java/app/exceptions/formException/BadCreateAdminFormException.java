package app.exceptions.formException;

public class BadCreateAdminFormException extends FormException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8881124919078589563L;

	public BadCreateAdminFormException(String errorMessage) {
        super(errorMessage);
    }

}
