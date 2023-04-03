package com.ayanokoujifl.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayanokoujifl.backend.entities.Estado;
import com.ayanokoujifl.backend.repositories.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	EstadoRepository repository;


	public List<Estado> findAll() {
		List<Estado> obj = repository.findAll();
		return obj;
	}
}
