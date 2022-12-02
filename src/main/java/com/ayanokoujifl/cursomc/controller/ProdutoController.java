package com.ayanokoujifl.cursomc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayanokoujifl.cursomc.controller.utils.URL;
import com.ayanokoujifl.cursomc.dto.ProdutoDTO;
import com.ayanokoujifl.cursomc.entities.Produto;
import com.ayanokoujifl.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {
	@Autowired
	ProdutoService service;

	@RequestMapping(value = "/{id}")
	public ResponseEntity<Produto> findById(@PathVariable Integer id) {
		Produto produto = service.findById(id);
		return ResponseEntity.ok().body(produto);
	}

	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> findPage(@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		List<Integer> ids = URL.decodeIntList(categorias);
		String nomeEncoded = URL.decodeParam(nome);
		Page<Produto> produtos = service.search(nomeEncoded, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDto = produtos.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listDto);

	}
}