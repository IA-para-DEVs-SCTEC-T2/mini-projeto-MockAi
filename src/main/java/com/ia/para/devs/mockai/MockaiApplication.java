package com.ia.para.devs.mockai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ia.para.devs.mockai.infrastructure.config.DotEnvInitializer;

@SpringBootApplication
public class MockaiApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MockaiApplication.class);
		app.addInitializers(new DotEnvInitializer());
		app.run(args);
	}

}
