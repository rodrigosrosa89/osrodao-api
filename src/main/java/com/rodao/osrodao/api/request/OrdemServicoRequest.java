package com.rodao.osrodao.api.request;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OrdemServicoRequest {

	@NotBlank
	private String descricao;

	@NotNull
	private BigDecimal preco;

	@Valid
	@NotNull
	private ClienteIdRequest clienteId;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public ClienteIdRequest getClienteId() {
		return clienteId;
	}

	public void setClienteId(ClienteIdRequest clienteId) {
		this.clienteId = clienteId;
	}

}
