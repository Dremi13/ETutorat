package app.etutorat.services;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.etutorat.dao.AdminRepository;
import app.etutorat.dao.SuperAdminRepository;
import app.etutorat.dao.TuteurRepository;
import app.etutorat.dao.TutoreRepository;
import app.etutorat.models.Etudiant;
import app.etutorat.models.Tuteur;
import app.etutorat.models.Tutore;
import app.etutorat.models.Utilisateur;
import app.etutorat.models.requestobjects.forms.EtudiantForm;
import app.etutorat.models.requestobjects.forms.SigninForm;
import static app.etutorat.utils.HashPswd.*;
import app.etutorat.models.requestobjects.UserToken;
import app.exceptions.WrongLoginPasswordException;
import app.exceptions.formException.BadRegisterFormException;
import app.exceptions.UserNotSignedInException;

@Service
@Transactional
public class AuthentificationService {

	@Autowired
	private TuteurRepository tur;
	
	@Autowired
	private TutoreRepository tor;
	
	@Autowired
	private AdminRepository ar;
	
	@Autowired
	private SuperAdminRepository sar;
	
	
	@Autowired
	private HttpSession httpSession;
	
	
	
	
	
	public UserToken checkSignin() throws UserNotSignedInException {
		
		
		//Si session déjà ouverte :

		 if(this.httpSession.getAttribute("token") != null) {
			 return (UserToken) httpSession.getAttribute("token");
		 }
		  
		 else throw new UserNotSignedInException();
	}
	
	
	public UserToken signin(SigninForm form) throws WrongLoginPasswordException {
		
		
		Etudiant etu;
		UserToken token = new UserToken();
		
		etu = tur.findByCodeetu(form.getLogin());
		
		
		try {
			
			
			if(etu == null) {
				etu = tor.findByCodeetu(form.getLogin());
				if(etu == null) {
					throw new WrongLoginPasswordException(form.getLogin());
				}
				else {
					if(!checkPassword(form.getPassword(),etu)) throw new WrongLoginPasswordException(form.getLogin());
					
					token.setType("tutore");
				}
			}
			else {
				if(!checkPassword(form.getPassword(),etu)) throw new WrongLoginPasswordException(form.getLogin());
				token.setType("tuteur");
			}
			token.setId(etu.getId());
			token.setLogin(etu.getCodeetu());
			this.httpSession.setAttribute("token", token);
		
		} catch(NoSuchProviderException | NoSuchAlgorithmException ex) {
			//Grosse erreur
			ex.printStackTrace();
		}
		
		
		return token;
	}
	
	
	public UserToken adminSignin(SigninForm form) throws WrongLoginPasswordException {
		
		
		Utilisateur admin;
		UserToken token = new UserToken();

		admin = ar.findByEmail(form.getLogin());
		
		
		try {
			
			
			if(admin == null) {
				admin = sar.findByEmail(form.getLogin());
				
				if(admin == null) {
					System.out.println("NULL");
					throw new WrongLoginPasswordException(form.getLogin());
				}
				else {
					if(!checkPassword(form.getPassword(),admin)) throw new WrongLoginPasswordException(form.getLogin());
					token.setType("superAdmin");
				}
			}
			else {
				if(!checkPassword(form.getPassword(),admin)) throw new WrongLoginPasswordException(form.getLogin());
				token.setType("admin");
			}
			
			token.setId(admin.getId());
			token.setLogin(admin.getEmail());
			this.httpSession.setAttribute("token", token);
		
		} catch(NoSuchProviderException | NoSuchAlgorithmException ex) {
			//Grosse erreur
			ex.printStackTrace();
		}
		
		
		return token;
	}
	
	
	public void signout() throws UserNotSignedInException {
		if(this.httpSession.getAttribute("token") != null) {
			 httpSession.removeAttribute("token");
		 }
		  
		 else throw new UserNotSignedInException();
	}
	
	
	
	//Register des tuteurs
	public UserToken registerTuteur(EtudiantForm form) throws BadRegisterFormException {
		
	
		UserToken token = new UserToken();
		
        try {
        	byte[][] hash = hashPassword(form.getPassword());
        	Tuteur t = new Tuteur(form.getNom(),form.getPrenom(),form.getEmail(),hash[1],hash[0],form.getCodeetu(),form.getTelephone(),form.getFiliere(),false);
	        token.setLogin(form.getCodeetu());
	        token.setType("tuteur");
	        
	        
	        Tuteur tsaved = tur.save(t);
	        token.setId(tsaved.getId());
	        
	        this.httpSession.setAttribute("token", token);
	       
        	
        } catch (NoSuchProviderException | NoSuchAlgorithmException ex) {
        	ex.printStackTrace();
        }
        
        
        return token;

	}
	
	//Register des tutores
		public UserToken registerTutore(EtudiantForm form) throws BadRegisterFormException {
			
		
			UserToken token = new UserToken();
			
	        try {
	        	byte[][] hash = hashPassword(form.getPassword());
	        	Tutore t = new Tutore(form.getNom(),form.getPrenom(),form.getEmail(),hash[1],hash[0],form.getCodeetu(),form.getTelephone(),form.getFiliere());
		        token.setLogin(form.getCodeetu());
		        token.setType("tutore");
		        
		        Tutore tsaved = tor.save(t);
		        token.setId(tsaved.getId());
		        
		        this.httpSession.setAttribute("token", token);
		       
	        	
	        } catch (NoSuchProviderException | NoSuchAlgorithmException ex) {
	        	ex.printStackTrace();
	        }
	        
	        
	        return token;

		}
	


	

}
