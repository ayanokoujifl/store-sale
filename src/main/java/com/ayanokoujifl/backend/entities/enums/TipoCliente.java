package com.ayanokoujifl.backend.entities.enums;

public enum TipoCliente {
	PESSOAFISICA(1, "Pessoa física"), PESSOAJURIDICA(2, "Pessoa jurídica");

	private int cod;
	private String descricao;

	private TipoCliente() {
		// TODO Auto-generated constructor stub
	}

	private TipoCliente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoCliente toEnum(Integer cod) {
		if(cod==null) {
			return null;
		}else {
			for(TipoCliente x : TipoCliente.values()) {
				if(cod.equals(x.getCod())) {
					return x;
				}
			}
			throw new IllegalArgumentException("Id invalido! Id: "+cod);
		}
	}
}
