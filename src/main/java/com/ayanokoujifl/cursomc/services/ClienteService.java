package com.ayanokoujifl.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayanokoujifl.cursomc.entities.Cliente;
import com.ayanokoujifl.cursomc.repositories.ClienteRepository;
import com.ayanokoujifl.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository repository;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj=repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Cliente não encontrado! Id: " + id + ", Tipo: " + ClienteService.class.getName()));
	}
}
