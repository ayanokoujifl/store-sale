package com.ayanokoujifl.backend.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ayanokoujifl.backend.entities.Categoria;
import com.ayanokoujifl.backend.entities.Cidade;
import com.ayanokoujifl.backend.entities.Cliente;
import com.ayanokoujifl.backend.entities.Endereco;
import com.ayanokoujifl.backend.entities.Estado;
import com.ayanokoujifl.backend.entities.ItemPedido;
import com.ayanokoujifl.backend.entities.Pagamento;
import com.ayanokoujifl.backend.entities.PagamentoComBoleto;
import com.ayanokoujifl.backend.entities.PagamentoComCartao;
import com.ayanokoujifl.backend.entities.Pedido;
import com.ayanokoujifl.backend.entities.Produto;
import com.ayanokoujifl.backend.entities.enums.EstadoPagamento;
import com.ayanokoujifl.backend.entities.enums.Perfil;
import com.ayanokoujifl.backend.entities.enums.TipoCliente;
import com.ayanokoujifl.backend.repositories.CategoriaRepository;
import com.ayanokoujifl.backend.repositories.CidadeRepository;
import com.ayanokoujifl.backend.repositories.ClienteRepository;
import com.ayanokoujifl.backend.repositories.EnderecoRepository;
import com.ayanokoujifl.backend.repositories.EstadoRepository;
import com.ayanokoujifl.backend.repositories.ItemPedidoRepository;
import com.ayanokoujifl.backend.repositories.PagamentoRepository;
import com.ayanokoujifl.backend.repositories.PedidoRepository;
import com.ayanokoujifl.backend.repositories.ProdutoRepository;

@Service
public class DBService {

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

	@Autowired
	BCryptPasswordEncoder be;

	public void instantiateTestDatabase() throws ParseException {
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
		Produto p4 = new Produto(null, "Mesa de escritório", 300.00);
		Produto p5 = new Produto(null, "Toalha", 50.00);
		Produto p6 = new Produto(null, "Colcha", 200.00);
		Produto p7 = new Produto(null, "TV true color", 1200.00);
		Produto p8 = new Produto(null, "Roçadeira", 800.00);
		Produto p9 = new Produto(null, "Abajour", 100.00);
		Produto p10 = new Produto(null, "Pendente", 180.00);
		Produto p11 = new Produto(null, "Shampoo", 90.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));

		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		Cliente cli1 = new Cliente(null, "Thaissa Silva", "thaissasuellensilva@gmail.com", "05761305674", TipoCliente.PESSOAFISICA,
				be.encode("123"));
		cli1.getTelefones().addAll(Arrays.asList("38997569902", "38999707497"));

		Cliente cli2 = new Cliente(null, "Luis", "guleite3@gmail.com", "16564423616", TipoCliente.PESSOAFISICA,
				be.encode("123"));
		cli1.getTelefones().addAll(Arrays.asList("38997569906", "38999707493"));
		cli2.addPerfil(Perfil.ADMIN);
		Endereco e1 = new Endereco(null, "Rua flores", "212", "casa", "Primeiro de maio", "39670-000", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida matos", "105", "casa", "São Geraldo", "39705-000", cli1, c2);
		Endereco e3 = new Endereco(null, "Primeiro de maio", "212", "casa", "Primeiro de maio", "39670-000", cli2, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		cli2.getEnderecos().addAll(Arrays.asList(e3));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"),
				null);
		ped2.setPagamento(pagto2);

		cli1.setPedidos(Arrays.asList(ped1, ped2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 2000.00, 1, 00.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 80.00, 2, 00.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 800.00, 1, 100.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		estadoRepository.saveAllAndFlush(Arrays.asList(est1, est2));
		cidadeRepository.saveAllAndFlush(Arrays.asList(c1, c2, c3));
		categoriaRepository.saveAllAndFlush(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAllAndFlush(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		clienteRepository.saveAllAndFlush(Arrays.asList(cli1,cli2));
		enderecoRepository.saveAllAndFlush(Arrays.asList(e1, e2));
		pedidoRepository.saveAllAndFlush(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAllAndFlush(Arrays.asList(pagto1, pagto2));
		itemPedidoRepository.saveAllAndFlush(Arrays.asList(ip1, ip2, ip3));
	}

}
