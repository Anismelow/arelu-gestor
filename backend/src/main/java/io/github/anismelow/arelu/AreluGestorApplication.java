package io.github.anismelow.arelu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan 
public class AreluGestorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AreluGestorApplication.class, args);
	}

}
