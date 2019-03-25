package app.exceptions.formException;

public class BadSigninFormException extends FormException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 802971387971085376L;

	public BadSigninFormException(String errorMessage) {
		super(errorMessage);
	}

}
