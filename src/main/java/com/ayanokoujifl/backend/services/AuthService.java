package com.ayanokoujifl.backend.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ayanokoujifl.backend.entities.Cliente;
import com.ayanokoujifl.backend.repositories.ClienteRepository;
import com.ayanokoujifl.backend.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private BCryptPasswordEncoder be;

	@Autowired
	private EmailService emailService;

	private Random rand = new Random();

	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if (cliente == null) {
			throw new ObjectNotFoundException("Cliente não encontrado");
		}
		String newPass = newPassword();
		cliente.setSenha(be.encode(newPass));
		clienteRepository.saveAndFlush(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		char vet[] = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if(opt==0) { //gera um digito
			return (char)(rand.nextInt(10)+48);	
		}else if(opt==1) { //gera letra maiuscula
			return (char)(rand.nextInt(26)+65);
		}else { //gera letra minuscula
			return (char)(rand.nextInt(26)+97);
		}
	}

}
