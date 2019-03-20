package app.etutorat.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

import app.etutorat.models.Utilisateur;

public class HashPswd {

	public HashPswd() {}
	

	private static final Random sr = new SecureRandom();
	
	
	public static boolean checkPassword(String password, Utilisateur user) throws NoSuchProviderException, NoSuchAlgorithmException {
		
		System.out.println("salt de " + user);
			
		MessageDigest md = MessageDigest.getInstance("SHA-256");
	    md.update(user.getSalt());
	    byte[] bytes = md.digest(password.getBytes());
	        
	    return Arrays.equals(bytes,user.getPassword());
	        
	        
	}	
		
		
	public static byte[][] hashPassword(String password) throws NoSuchProviderException, NoSuchAlgorithmException{
			
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
