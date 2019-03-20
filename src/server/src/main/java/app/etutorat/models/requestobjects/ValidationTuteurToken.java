package app.etutorat.models.requestobjects;

import java.io.Serializable;

public class ValidationTuteurToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2630601415035764934L;
	
	
	private Long id;
	private boolean validation;
	
	
	public ValidationTuteurToken() {}
	
	public ValidationTuteurToken(Long id, boolean validation) {
		this.id = id;
		this.validation = validation;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean getValidation() {
		return validation;
	}
	public void setValidation(boolean validation) {
		this.validation = validation;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
