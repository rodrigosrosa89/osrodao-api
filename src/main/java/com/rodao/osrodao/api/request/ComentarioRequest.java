package com.rodao.osrodao.api.request;

import javax.validation.constraints.NotBlank;

public class ComentarioRequest {
	
	@NotBlank
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
