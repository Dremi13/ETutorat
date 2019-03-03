package app;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"app"})
public class EtutoratApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtutoratApplication.class, args);
	}

}

