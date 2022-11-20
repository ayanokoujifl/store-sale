package com.ayanokoujifl.cursomc.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class ItemPedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ItemPedidoPk id = new ItemPedidoPk();
	private Double preco;
	private Integer quantidade;
	private Double desconto;

	public ItemPedido() {
		// TODO Auto-generated constructor stub
	}

	public ItemPedido(Pedido pedido, Produto produto, Double preco, Integer quantidade, Double desconto) {
		super();
		id.setPedido(pedido);
		id.setProduto(produto);
		this.preco = preco;
		this.quantidade = quantidade;
		this.desconto = desconto;
	}

	public ItemPedidoPk getId() {
		return id;
	}

	public void setId(ItemPedidoPk id) {
		this.id = id;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Pedido getPedido() {
		return id.getPedido();
	}

	public Produto getProduto() {
		return id.getProduto();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPedido other = (ItemPedido) obj;
		return Objects.equals(id, other.id);
	}

}
