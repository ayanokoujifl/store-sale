package com.ayanokoujifl.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayanokoujifl.cursomc.entities.ItemPedido;
import com.ayanokoujifl.cursomc.entities.PagamentoComBoleto;
import com.ayanokoujifl.cursomc.entities.Pedido;
import com.ayanokoujifl.cursomc.entities.Produto;
import com.ayanokoujifl.cursomc.entities.enums.EstadoPagamento;
import com.ayanokoujifl.cursomc.repositories.ItemPedidoRepository;
import com.ayanokoujifl.cursomc.repositories.PagamentoRepository;
import com.ayanokoujifl.cursomc.repositories.PedidoRepository;
import com.ayanokoujifl.cursomc.repositories.ProdutoRepository;
import com.ayanokoujifl.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	PedidoRepository repository;

	@Autowired
	BoletoService boletoService;

	@Autowired
	PagamentoRepository pagamentoRepository;

	@Autowired
	ProdutoRepository produtoRepository;

	@Autowired
	ItemPedidoRepository itemPedidoRepository;

	public Pedido findById(Integer id) {
		Optional<Pedido> pedido = repository.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException(
				"Pedido não encontrado! Id: " + id + ", Tipo: " + PedidoService.class.getName()));
	}

	public Pedido save(Pedido obj) {
		obj.setId(null);
		obj.setInstant(new Date());
		obj.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstant());
		}
		obj = repository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			Produto p = produtoRepository.findById(ip.getProduto().getId()).get();
			ip.setPreco(p.getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}

}
