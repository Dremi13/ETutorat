package app.etutorat.models.requestobjects.forms;

import java.io.Serializable;

import app.etutorat.models.requestobjects.Form;
import app.exceptions.formException.BadSigninFormException;

public class SigninForm implements Serializable, Form {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1705896937424269195L;

	
	private String login;
	private String password;
	
	
	public SigninForm() {}
	
	public SigninForm(String login, String password) {
		this.login = login;
		this.password = password;
	}
	
	public void isValid() throws BadSigninFormException {
		
		boolean res = true;
		String message = "";
		
		if(this.getLogin() == null || this.getLogin().equals("")) 											{ res = false; message += " login required"; 	}
		if(this.getPassword() == null) 																		{ res = false; message += " password required"; 	}
		
		if(this.getPassword().length() < 4 || this.getPassword().length() > 16) 							{ res = false; message += " wrong password size"; 	}
		
		if(!res) throw new BadSigninFormException(message);
	}
	
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
