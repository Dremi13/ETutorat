package app.etutorat.models.requestobjects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import app.etutorat.models.deserializer.FormDeserializer;
import app.exceptions.formException.FormException;

@JsonDeserialize(using = FormDeserializer.class)
public interface Form {
	
	
	public void isValid() throws FormException;
	

}
