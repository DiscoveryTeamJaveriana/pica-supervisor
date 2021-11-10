package co.edu.javeriana.discovery.pica.supervisor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PicaSupervisorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PicaSupervisorApplication.class, args);
	}

}
