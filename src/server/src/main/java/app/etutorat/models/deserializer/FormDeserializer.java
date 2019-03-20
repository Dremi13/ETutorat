package app.etutorat.models.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import app.etutorat.models.requestobjects.Form;
import app.etutorat.models.requestobjects.forms.CreateAdminForm;
import app.etutorat.models.requestobjects.forms.EtudiantForm;
import app.etutorat.models.requestobjects.forms.SigninForm;
import app.etutorat.models.requestobjects.forms.UpdateForm;

public class FormDeserializer extends StdDeserializer<Form> {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5998046679096265280L;


	public FormDeserializer() { 
        this(null); 
    } 
 
    public FormDeserializer(Class<?> vc) { 
        super(vc); 
    }

    
    @Override
    public Form deserialize(JsonParser jp, DeserializationContext ctxt)	 throws IOException, JsonProcessingException {
    	
    	
        ObjectCodec oc = jp.getCodec();
    	JsonNode node = oc.readTree(jp);
    	
    	
        // Déterminer quel type est le form (on triera du plus fourni au moins fourni) :
        
        //Update
        if(node.get("id") != null)
        	{
        		Long id = node.get("id").asLong();
        		JsonNode n = node.get("form");
        		
        		return new UpdateForm(id,oc.treeToValue(n, Form.class));
        	}
        else {
        	
	        //Etudiant
	        if(node.get("codeetu") != null) {
	        	return new EtudiantForm(node.get("nom").asText(),node.get("prenom").asText(),node.get("email").asText(),node.get("password").asText(),node.get("codeetu").asText(),node.get("telephone").asText(),node.get("filiere").asText());
	        }
	        else if(node.get("email") != null){
	        	return new CreateAdminForm(node.get("nom").asText(),node.get("prenom").asText(),node.get("email").asText(),node.get("password").asText());
	        }
	        
	        else 
	        	return new SigninForm(node.get("login").asText(),node.get("password").asText());
        }
        
    }
}
