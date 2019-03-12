package app.etutorat.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.etutorat.dao.AdminRepository;
import app.etutorat.dao.SuperAdminRepository;
import app.etutorat.dao.TuteurRepository;
import app.etutorat.dao.TutoreRepository;
import app.etutorat.models.Administrateur;
import app.etutorat.models.Etudiant;
import app.etutorat.models.Tuteur;
import app.etutorat.models.Utilisateur;
import app.etutorat.models.requestobjects.RegisterTuteurForm;
import app.etutorat.models.requestobjects.SigninForm;
import app.etutorat.models.requestobjects.UserToken;
import app.exceptions.WrongLoginPasswordException;
import app.exceptions.BadRegisterFormException;
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
	
	
	private static final Random sr = new SecureRandom();
	
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
	public UserToken register(RegisterTuteurForm form) throws BadRegisterFormException {
		
		//Verification des champs
		if(form.getPassword().length() < 4 || form.getPassword().length() > 16) {
			throw new BadRegisterFormException("wrong password size");
		}
		
		 
		UserToken token = new UserToken();
		
        try {
        	byte[][] hash = hashPassword(form.getPassword());
        	Tuteur t = new Tuteur(form.getNom(),form.getPrenom(),form.getEmail(),hash[1],hash[0],form.getCodeetu(),form.getTelephone(),form.getFiliere(),false);
	        token.setLogin(form.getCodeetu());
	        token.setType("tuteur");
	        
	        tur.save(t);
	        
	        this.httpSession.setAttribute("token", token);
	       
        	
        } catch (NoSuchProviderException | NoSuchAlgorithmException ex) {
        	ex.printStackTrace();
        }
        
        
        return token;

	}
	

	public boolean checkPassword(String password, Utilisateur user) throws NoSuchProviderException, NoSuchAlgorithmException {
			
		System.out.println("salt de " + user);
			
		MessageDigest md = MessageDigest.getInstance("SHA-256");
	    md.update(user.getSalt());
	    byte[] bytes = md.digest(password.getBytes());
	        
	    return Arrays.equals(bytes,user.getPassword());
	        
	        
	}	
		
		
	public byte[][] hashPassword(String password) throws NoSuchProviderException, NoSuchAlgorithmException{
			
		byte[][] result = new byte[2][];
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		result[0] = new byte[16];
		result[1] = new byte[16];
			
		sr.nextBytes(result[0]);;
			
			
        md.update(result[0]);
        result[1] = md.digest(password.getBytes());
	        
        return result;
		}
	
	

}
