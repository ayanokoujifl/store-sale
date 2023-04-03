package com.ayanokoujifl.backend.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayanokoujifl.backend.dto.CidadeDTO;
import com.ayanokoujifl.backend.dto.EstadoDTO;
import com.ayanokoujifl.backend.entities.Cidade;
import com.ayanokoujifl.backend.entities.Estado;
import com.ayanokoujifl.backend.services.CidadeService;
import com.ayanokoujifl.backend.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoController {

	@Autowired
	EstadoService service;

	@Autowired
	CidadeService cidadeService;

	@GetMapping
	public ResponseEntity<List<EstadoDTO>> findAll(){
		List<Estado> list=service.findAll();
		List<EstadoDTO> listDto= list.stream().map(x-> new EstadoDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@GetMapping(value = "/{id}/cidades")
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer id){
		List<Cidade>list= cidadeService.findByEstado(id);
		List<CidadeDTO> listDto = list.stream().map(obj-> new CidadeDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

}
