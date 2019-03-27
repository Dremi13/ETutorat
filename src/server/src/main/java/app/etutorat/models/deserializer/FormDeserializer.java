package app.etutorat.models.deserializer;

import java.io.IOException;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;


import app.etutorat.models.Salle;
import app.etutorat.models.Tuteur;
import app.etutorat.models.requestobjects.Form;
import app.etutorat.models.requestobjects.forms.CreateAdminForm;
import app.etutorat.models.requestobjects.forms.EtudiantForm;
import app.etutorat.models.requestobjects.forms.SeanceForm;
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
    	
    	System.out.println("Start deserialize");
    	
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
	        //Admin
	        else if(node.get("email") != null){
	        	return new CreateAdminForm(node.get("nom").asText(),node.get("prenom").asText(),node.get("email").asText(),node.get("password").asText());
	        }
	        //Seance
	        else if(node.get("sujet") != null) {
	        	
	        	System.out.println(node.asText());
	        	
	        	JsonNode nodeTuteur = node.get("tuteur");
	        	JsonNode nodeSalle = node.get("salle");
	        	
	        	
	        	return new SeanceForm(	OffsetDateTime.parse(node.get("start").asText()),
	        							OffsetDateTime.parse(node.get("end").asText()),
	        							node.get("outilAV").asText(),
	        							node.get("sujet").asText(),
	        							node.get("nbmaxtutores").asInt(),
	        							oc.treeToValue(nodeTuteur, Tuteur.class),
	        							oc.treeToValue(nodeSalle, Salle.class));
	        }
	        
	        
	        //Signin
	        else 
	        	return new SigninForm(node.get("login").asText(),node.get("password").asText());
        }
        
    }
    
    
    
}
