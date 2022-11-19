package com.ayanokoujifl.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ayanokoujifl.cursomc.entities.Categoria;
import com.ayanokoujifl.cursomc.entities.Cidade;
import com.ayanokoujifl.cursomc.entities.Cliente;
import com.ayanokoujifl.cursomc.entities.Endereco;
import com.ayanokoujifl.cursomc.entities.Estado;
import com.ayanokoujifl.cursomc.entities.Produto;
import com.ayanokoujifl.cursomc.entities.enums.TipoCliente;
import com.ayanokoujifl.cursomc.repositories.CategoriaRepository;
import com.ayanokoujifl.cursomc.repositories.CidadeRepository;
import com.ayanokoujifl.cursomc.repositories.ClienteRepository;
import com.ayanokoujifl.cursomc.repositories.EnderecoRepository;
import com.ayanokoujifl.cursomc.repositories.EstadoRepository;
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

	@Autowired
	CidadeRepository cidadeRepository;

	@Autowired
	EstadoRepository estadoRepository;

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	EnderecoRepository enderecoRepository;

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

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "16564423616", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("38997569902", "38999707497"));

		Endereco e1 = new Endereco(null, "Rua flores", "212", "casa", "Primeiro de maio", "39670-000", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida matos", "105", "casa", "São Geraldo", "39705-000", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		estadoRepository.saveAllAndFlush(Arrays.asList(est1, est2));
		cidadeRepository.saveAllAndFlush(Arrays.asList(c1, c2, c3));
		categoriaRepository.saveAllAndFlush(Arrays.asList(cat1, cat2));
		produtoRepository.saveAllAndFlush(Arrays.asList(p1, p2, p3));
		clienteRepository.saveAllAndFlush(Arrays.asList(cli1));
		enderecoRepository.saveAllAndFlush(Arrays.asList(e1, e2));
	}
}
