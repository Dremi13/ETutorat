package app.etutorat.models;



import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;




@MappedSuperclass
public abstract class Utilisateur {

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	
	private String nom;
	
	
	private String prenom;
	
	@Column(unique = true)
	private String email;
	
	@Lob
	private byte[] password;
	
	@Lob
	private byte[] salt;
	
	public Utilisateur() {}
	
	public Utilisateur(
			String nom,
			String prenom,
			String email,
			byte[] password,
			byte[] salt) 
	{
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.password = password;
		this.salt = salt;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public byte[] getPassword() {
		return password;
	}
	public void setPassword(byte[] password) {
		this.password = password;
	}
	public byte[] getSalt() {
		return salt;
	}
	public void setSalt(byte[] salt) {
		this.salt = salt;
	}
	
	
	
	
}
