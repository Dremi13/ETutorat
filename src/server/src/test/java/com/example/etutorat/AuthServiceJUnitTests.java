package com.example.etutorat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


import app.EtutoratApplication;
import app.etutorat.dao.TuteurRepository;
import app.etutorat.dao.TutoreRepository;
import app.etutorat.models.Tuteur;
import app.etutorat.models.Tutore;

import static app.etutorat.utils.HashPswd.*;

import app.etutorat.models.requestobjects.forms.SigninForm;
import app.etutorat.models.requestobjects.UserToken;
import app.etutorat.services.AuthentificationService;
import app.exceptions.WrongLoginPasswordException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EtutoratApplication.class)
public class AuthServiceJUnitTests {
	
	@MockBean
	TuteurRepository turRe;
	@MockBean 
	TutoreRepository torRe;
	
	
	@MockBean
	Tuteur tur;
	@MockBean
	Tutore tor;
	
	private static final Random sr = new SecureRandom();
	
	@Autowired
	AuthentificationService as;
	
	/*@BeforeClass
	public static void setup()  {
		MockitoAnnotations.initMocks(this);
	}*/
	
	
	
	@Before
	public void setupData() throws NoSuchAlgorithmException {
		Mockito.when(turRe.findByCodeetu("a12345678")).thenReturn(tur);
		Mockito.when(torRe.findByCodeetu("b12345678")).thenReturn(tor);
		
		Mockito.when(tur.getCodeetu()).thenReturn("a12345678");
		Mockito.when(tor.getCodeetu()).thenReturn("b12345678");
		
		//Hachage de deux passwords de test
		MessageDigest md = MessageDigest.getInstance("SHA-256");	
		
		
		byte[] salt = new byte[16];
		sr.nextBytes(salt);	
        md.update(salt);
        byte[] hash = md.digest("azer".getBytes());
        
        byte[] salt2 = new byte[16];
		sr.nextBytes(salt2);	
        md.update(salt2);
        byte[] hash2 = md.digest("azer".getBytes());
        
		
		Mockito.when(tur.getPassword()).thenReturn(hash);
		Mockito.when(tur.getSalt()).thenReturn(salt);
		Mockito.when(tor.getPassword()).thenReturn(hash2);
		Mockito.when(tor.getSalt()).thenReturn(salt2);
	}
	
	
	@Test
	public void testCheckPswdRight() throws NoSuchProviderException, NoSuchAlgorithmException {
		Assert.assertTrue(checkPassword("azer", this.tur));
		Assert.assertTrue(checkPassword("azer", this.tor));
	}
	
	@Test
	public void testCheckPswdWrong() throws NoSuchProviderException, NoSuchAlgorithmException {
		Assert.assertFalse(checkPassword("bzer", this.tur));
		Assert.assertFalse(checkPassword("azebzer", this.tor));
	}
	
	@Test
	public void testSignininRight() throws WrongLoginPasswordException {
		
		
		UserToken token = as.signin(new SigninForm("a12345678","azer"));
		Assert.assertEquals(token,new UserToken("a12345678","tuteur"));
		
		token = as.signin(new SigninForm("b12345678","azer"));
		Assert.assertEquals(token,new UserToken("b12345678","tutore"));
		
		
	}
	
	@Test(expected = WrongLoginPasswordException.class)
	public void testSignininWrongLogin() throws WrongLoginPasswordException {
		as.signin(new SigninForm("c12345678","azer"));
	}
	
	@Test(expected = WrongLoginPasswordException.class)
	public void testSignininWrongPswd() throws WrongLoginPasswordException {
		as.signin(new SigninForm("a12345678","azerd"));
	}
	
	
	//register
	
}
