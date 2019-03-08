package app.exceptions;

@SuppressWarnings("serial")
public class UserNotSignedInException extends Exception {

	public UserNotSignedInException() {
		super("User not signed in");
	}

	
	
}
