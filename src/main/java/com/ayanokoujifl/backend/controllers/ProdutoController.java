package com.ayanokoujifl.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayanokoujifl.backend.controllers.utils.URL;
import com.ayanokoujifl.backend.dto.ProdutoDTO;
import com.ayanokoujifl.backend.entities.Produto;
import com.ayanokoujifl.backend.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {
	@Autowired
	ProdutoService service;

	@GetMapping("/{id}")
	public ResponseEntity<Produto> findById(@PathVariable Integer id) {
		Produto produto = service.findById(id);
		return ResponseEntity.ok().body(produto);
	}

	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> findPage(@RequestParam(defaultValue = "") String nome,
			@RequestParam(defaultValue = "") String categorias,
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "24") Integer linesPerPage,
			@RequestParam(defaultValue = "nome") String orderBy,
			@RequestParam(defaultValue = "ASC") String direction) {
		List<Integer> ids = URL.decodeIntList(categorias);
		String nomeEncoded = URL.decodeParam(nome);
		Page<Produto> produtos = service.search(nomeEncoded, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDto = produtos.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listDto);

	}
}