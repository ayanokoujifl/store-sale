package com.ayanokoujifl.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ayanokoujifl.cursomc.services.DBService;
import com.ayanokoujifl.cursomc.services.EmailService;
import com.ayanokoujifl.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
@ComponentScan("com.ayanokoujifl.cursomc")
public class TestConfig {

	@Autowired
	DBService dbService;

	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
