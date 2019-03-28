package app.etutorat.models.requestobjects;

import java.io.Serializable;


public class UserToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9169252046663449859L;
	
	private long id;
	private String login;
	private String type;
	private boolean permission;
	
	public UserToken() {}
	
	public UserToken(String login, String type) {
		this.login = login;
		this.type = type;
	}
	
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	

	@Override
	public boolean equals(Object obj) {
		UserToken token = (UserToken) obj;
		return (this.id == token.id && this.type == token.type);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isPermission() {
		return permission;
	}

	public void setPermission(boolean permission) {
		this.permission = permission;
	}


	
	
	
	
	

}
