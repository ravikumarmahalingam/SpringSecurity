package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@ComponentScan({("com.paypal.controller"), ("com.paypal.security"), ("com.paypal.service"), ("com.paypal.authentication")})
//The below two annotations are required if we are creating our own tables for authentication.
@EnableJpaRepositories("com.paypal.repository")
@EntityScan("com.paypal.model")
//The below annotation is to get the list of filters in the console after the start up.
@EnableWebSecurity(debug = true)
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}