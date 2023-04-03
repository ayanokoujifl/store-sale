package com.ayanokoujifl.backend.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ayanokoujifl.backend.services.DBService;
import com.ayanokoujifl.backend.services.EmailService;
import com.ayanokoujifl.backend.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	DBService dbService;

    @Bean
    boolean instantiateDatabase() throws ParseException {
        dbService.instantiateTestDatabase();
        return true;
    }

    @Bean
    EmailService emailService() {
        return new MockEmailService();
    }
}
