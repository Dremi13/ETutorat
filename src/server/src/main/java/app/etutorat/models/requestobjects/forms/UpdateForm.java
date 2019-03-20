package app.etutorat.models.requestobjects.forms;

import java.io.Serializable;

import app.etutorat.models.requestobjects.Form;
import app.exceptions.formException.BadUpdateFormException;
import app.exceptions.formException.FormException;

public class UpdateForm implements Serializable, Form {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5465740648029971626L;

	
	private Long id;
	private Form form;
	
	public UpdateForm() {}
	
	public UpdateForm(Long id, Form form) {
		this.id = id;
		this.form = form;
	}
	
	public void isValid() throws BadUpdateFormException {
		String errorMessage = "";
		try {
			if(this.getId() == null) errorMessage += "Id required ";
			this.getForm().isValid();
			
		} catch (FormException ex) {
			throw new BadUpdateFormException(ex+errorMessage);
		}

	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Form getForm() {
		return form;
	}
	
	public void setForm(Form form) {
		this.form = form;
	}
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
