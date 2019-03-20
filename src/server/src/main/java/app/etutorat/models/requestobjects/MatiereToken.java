package app.etutorat.models.requestobjects;

import java.io.Serializable;

public class MatiereToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3722499654707586602L;
	
	private Long id;
	private String matiere;
	
	
	public MatiereToken() {}
	
	public MatiereToken(Long id, String comp) {
		this.id = id;
		this.matiere = comp;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMatiere() {
		return matiere;
	}
	public void setMatiere(String matiere) {
		this.matiere = matiere;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
