package com.ayanokoujifl.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayanokoujifl.cursomc.entities.Categoria;
import com.ayanokoujifl.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository repository;

	public Categoria findById(Integer id) {
		Optional<Categoria> obj = repository.findById(id);
		return obj.orElse(null);
	}

	public List<Categoria> findAll() {
		List<Categoria> obj = repository.findAll();
		return obj;
	}

}
