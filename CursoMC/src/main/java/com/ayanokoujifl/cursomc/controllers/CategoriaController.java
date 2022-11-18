package com.ayanokoujifl.cursomc.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayanokoujifl.cursomc.entities.Categoria;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

	@GetMapping
	public List<Categoria> listar() {
		Categoria cat1 = new Categoria(1, "Inform�tica");
		Categoria cat2 = new Categoria(2, "Escrit�rio");
		List<Categoria> lista = new ArrayList();
		lista.add(cat1);
		lista.add(cat2);
		return lista;
	}

}