package fr.vvlabs.hibernate.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HibernateSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(HibernateSampleApplication.class, args);
		System.out.println("--->>> SWAGGER at http://localhost:8080/swagger-ui.html <<<---");
		System.out.println("--->>> H2 CONSOLE at http://localhost:8080/h2-console/ <<<---");
	}
}
