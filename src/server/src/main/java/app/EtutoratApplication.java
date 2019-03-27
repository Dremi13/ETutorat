package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import static app.etutorat.utils.HashPswd.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import app.etutorat.dao.SalleRepository;
import app.etutorat.dao.SeanceRepository;
import app.etutorat.dao.SuperAdminRepository;
import app.etutorat.dao.TuteurRepository;
import app.etutorat.dao.TutoreRepository;
import app.etutorat.models.Salle;
import app.etutorat.models.Seance;
import app.etutorat.models.SuperAdministrateur;
import app.etutorat.models.Tuteur;
import app.etutorat.models.Tutore;
import app.etutorat.services.AdminService;

@SpringBootApplication(scanBasePackages = {"app"})
public class EtutoratApplication implements ApplicationRunner {

	
	@Autowired
	private SuperAdminRepository sar;
	
	
	@Autowired
	private TuteurRepository tur;
	
	
	@Autowired
	private TutoreRepository tor;
	
	
	@Autowired
	private SeanceRepository ser;
	
	@Autowired
	private SalleRepository salr;
	
	@Autowired
	private AdminService as;
	
	
	public static void main(String[] args) {
		SpringApplication.run(EtutoratApplication.class, args);
	}

	
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
	
		byte[][] r = hashPassword("root");
		SuperAdministrateur sa = new SuperAdministrateur("Root","admin","admin.root@root.com",r[1],r[0]);
		sar.save(sa);

		
		Tuteur tu = new Tuteur("tu","teur","ta@root.com",r[1],r[0], "b01234567", "0123456789", "Master 2 Informatique");
		tur.save(tu);
		tu = new Tuteur("tu","trice","tu@root.com",r[1],r[0], "c01234567", "0123456789", "Master 2 Informatique");
		tur.save(tu);
		
		
		tu = new Tuteur("tu","toyer","tut@root.com",r[1],r[0], "d01234567", "0123456789", "Master 2 Informatique");
		tur.save(tu);
		tu = new Tuteur("tru","che","tute@root.com",r[1],r[0], "e01234567", "0123456789", "Master 2 Informatique");
		tur.save(tu);
		
		
		Tutore to = new Tutore("tu","rbo","tut@root.com",r[1],r[0], "a01234567", "0123456789", "Master 2 Informatique");
		Tutore tForId = tor.save(to);
		to = new Tutore("trac","teur","tra@root.com",r[1],r[0], "z01234567", "0123456789", "Master 2 Informatique");
		Tutore tForId2 = tor.save(to);
		
		
		Salle s1 = new Salle("Salle 1","1","St-Charles",10);
		salr.save(s1);
		

		Salle s2 = new Salle("Salle 2","1","St-Charles",10);
		salr.save(s2);

		Salle s3 = new Salle("Salle 3","1","St-Charles",10);
		salr.save(s3);

		Salle s4 = new Salle("Salle 4","1","St-Charles",10);
		salr.save(s4);
		

		Salle s5 = new Salle("Salle 5","1","St-Charles",10);
		salr.save(s5);
		
		Salle s6 = new Salle("Salle 6","1","St-Charles",10);
		salr.save(s6);
		
		Seance s = new Seance(
				OffsetDateTime.of(2019,03,27,15,0,0,0,ZoneOffset.UTC),
				OffsetDateTime.of(2019,03,27,15,30,0,0,ZoneOffset.UTC),
				"",
				"Java",
				4,
				tu,
				s1);
		
		
		
		Seance sForId = ser.save(s);
		
		as.joinSeance(tForId.getId(), sForId.getId());
		as.joinSeance(tForId2.getId(), sForId.getId());
		as.joinSeance(tForId.getId(), sForId.getId());
		
		
		s = new Seance(
				OffsetDateTime.of(2019,03,28,15,0,0,0,ZoneOffset.UTC),
				OffsetDateTime.of(2019,03,28,16,30,0,0,ZoneOffset.UTC),
				"",
				"Java",
				4,
				tu,
				s1);
		ser.save(s);
		
		
	}
	
}

