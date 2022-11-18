package com.ayanokoujifl.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ayanokoujifl.cursomc.entities.Categoria;
import com.ayanokoujifl.cursomc.repositories.CategoriaRepository;

@SpringBootApplication
public class CursoMcApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CursoMcApplication.class, args);
	}

	@Autowired
	CategoriaRepository categoriaRepository;
	
	@Override
	public void run(String... args) throws Exception {
	Categoria cat1=new Categoria(null,"Informática");
	Categoria cat2=new Categoria(null,"Escritório");
	categoriaRepository.saveAllAndFlush(Arrays.asList(cat1,cat2));
	}
}
