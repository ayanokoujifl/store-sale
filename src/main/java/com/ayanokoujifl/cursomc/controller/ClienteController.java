package com.ayanokoujifl.cursomc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayanokoujifl.cursomc.entities.Cliente;
import com.ayanokoujifl.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

	@Autowired
	ClienteService service;

	@RequestMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		Cliente c = service.findById(id);
		return ResponseEntity.ok().body(c);
	}

}
