package com.rodao.osrodao.api.request;

import javax.validation.constraints.NotNull;

public class ClienteIdRequest {

	@NotNull
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
