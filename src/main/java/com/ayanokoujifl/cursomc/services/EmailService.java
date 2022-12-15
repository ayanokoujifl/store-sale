package com.ayanokoujifl.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.ayanokoujifl.cursomc.entities.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	void sendEmail(SimpleMailMessage msg);
	
}
