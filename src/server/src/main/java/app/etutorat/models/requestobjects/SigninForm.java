package app.etutorat.models.requestobjects;

import java.io.Serializable;

public class SigninForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1705896937424269195L;

	
	private String login;
	private String password;
	
	
	public SigninForm(String login, String password) {
		this.login = login;
		this.password = password;
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
