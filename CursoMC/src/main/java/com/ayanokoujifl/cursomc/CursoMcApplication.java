package com.ayanokoujifl.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ayanokoujifl.cursomc.entities.Categoria;
import com.ayanokoujifl.cursomc.entities.Produto;
import com.ayanokoujifl.cursomc.repositories.CategoriaRepository;
import com.ayanokoujifl.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursoMcApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CursoMcApplication.class, args);
	}

	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	ProdutoRepository produtoRepository;

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAllAndFlush(Arrays.asList(cat1, cat2));
		produtoRepository.saveAllAndFlush(Arrays.asList(p1, p2, p3));
	}
}
