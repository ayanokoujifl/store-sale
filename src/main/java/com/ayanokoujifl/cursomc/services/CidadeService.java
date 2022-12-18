package com.ayanokoujifl.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayanokoujifl.cursomc.entities.Cidade;
import com.ayanokoujifl.cursomc.repositories.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	CidadeRepository repository;
	
	public List<Cidade> findByEstado(Integer estadoId){
		return repository.findCidades(estadoId);
	}
	
}
