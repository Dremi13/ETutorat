package app.exceptions;

@SuppressWarnings("serial")
public class WrongLoginPasswordException extends Exception {
 
	
	public WrongLoginPasswordException(String errorMessage) {
        super(errorMessage);
    }
}
