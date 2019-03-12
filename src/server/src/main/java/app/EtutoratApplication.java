package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import app.etutorat.dao.SuperAdminRepository;
import app.etutorat.models.SuperAdminstrateur;
import app.etutorat.services.AuthentificationService;

@SpringBootApplication(scanBasePackages = {"app"})
public class EtutoratApplication implements ApplicationRunner {

	
	@Autowired
	private SuperAdminRepository sar;
	
	@Autowired
	private AuthentificationService as;
	
	
	public static void main(String[] args) {
		SpringApplication.run(EtutoratApplication.class, args);
	}

	
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
	
		byte[][] r = as.hashPassword("root");
		SuperAdminstrateur sa = new SuperAdminstrateur("Root","admin","admin.root@root.com",r[1],r[0]);
		sar.save(sa);

	}
	
}

