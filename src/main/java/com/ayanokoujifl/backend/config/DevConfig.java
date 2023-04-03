package com.ayanokoujifl.backend.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ayanokoujifl.backend.services.DBService;
import com.ayanokoujifl.backend.services.EmailService;
import com.ayanokoujifl.backend.services.SmtpEmailService;


@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	DBService dbService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

    @Bean
    boolean instantiateDatabase() throws ParseException {
        if (!"create".equals(strategy)) {
            return false;
        } else {
            dbService.instantiateTestDatabase();
            return true;
        }
    }

    @Bean
    EmailService emailService() {
        return new SmtpEmailService();
    }
}
