package com.rodao.osrodao.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodao.osrodao.domain.model.Cliente;

@RestController
public class ClienteController {
	
	@GetMapping("/clientes")
	public List<Cliente> listar() {
		Cliente cliente1 = new Cliente();
		cliente1.setId(1L);
		cliente1.setNome("Rodrigo Siqueira Rosa");
		cliente1.setEmail("rodrigo.rosa@email.com");
		cliente1.setTelefone("31991191366");
		Cliente cliente2 = new Cliente();
		cliente2.setId(2L);
		cliente2.setNome("Amanda Siqueira Rosa");
		cliente2.setEmail("amanda.rosa@email.com");
		cliente2.setTelefone("31999241366");
		return Arrays.asList(cliente1, cliente2);
	}

}
