package com.ayanokoujifl.cursomc;

import java.text.SimpleDateFormat;
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
import com.ayanokoujifl.cursomc.entities.ItemPedido;
import com.ayanokoujifl.cursomc.entities.Pagamento;
import com.ayanokoujifl.cursomc.entities.PagamentoComBoleto;
import com.ayanokoujifl.cursomc.entities.PagamentoComCartao;
import com.ayanokoujifl.cursomc.entities.Pedido;
import com.ayanokoujifl.cursomc.entities.Produto;
import com.ayanokoujifl.cursomc.entities.enums.EstadoPagamento;
import com.ayanokoujifl.cursomc.entities.enums.TipoCliente;
import com.ayanokoujifl.cursomc.repositories.CategoriaRepository;
import com.ayanokoujifl.cursomc.repositories.CidadeRepository;
import com.ayanokoujifl.cursomc.repositories.ClienteRepository;
import com.ayanokoujifl.cursomc.repositories.EnderecoRepository;
import com.ayanokoujifl.cursomc.repositories.EstadoRepository;
import com.ayanokoujifl.cursomc.repositories.ItemPedidoRepository;
import com.ayanokoujifl.cursomc.repositories.PagamentoRepository;
import com.ayanokoujifl.cursomc.repositories.PedidoRepository;
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

	@Autowired
	PedidoRepository pedidoRepository;

	@Autowired
	PagamentoRepository pagamentoRepository;

	@Autowired
	ItemPedidoRepository itemPedidoRepository;

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");

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

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"),
				null);
		ped2.setPagamento(pagto2);

		cli1.setPedidos(Arrays.asList(ped1, ped2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 00.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 00.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		estadoRepository.saveAllAndFlush(Arrays.asList(est1, est2));
		cidadeRepository.saveAllAndFlush(Arrays.asList(c1, c2, c3));
		categoriaRepository.saveAllAndFlush(Arrays.asList(cat1, cat2,cat3,cat4,cat5,cat6,cat7));
		produtoRepository.saveAllAndFlush(Arrays.asList(p1, p2, p3));
		clienteRepository.saveAllAndFlush(Arrays.asList(cli1));
		enderecoRepository.saveAllAndFlush(Arrays.asList(e1, e2));
		pedidoRepository.saveAllAndFlush(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAllAndFlush(Arrays.asList(pagto1, pagto2));
		itemPedidoRepository.saveAllAndFlush(Arrays.asList(ip1, ip2, ip3));
	}
}
