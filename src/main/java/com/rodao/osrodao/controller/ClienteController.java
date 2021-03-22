package com.rodao.osrodao.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rodao.osrodao.domain.model.Cliente;
import com.rodao.osrodao.domain.repository.ClienteRepository;
import com.rodao.osrodao.domain.service.CadastroClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CadastroClienteService cadastroClienteService;

	@GetMapping
	public List<Cliente> listarTodos() {
		return clienteRepository.findAll();
	}

	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> getById(@PathVariable("clienteId") Long clienteId) {
		Optional<Cliente> optional = clienteRepository.findById(clienteId);

		if (optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		return cadastroClienteService.salvar(cliente);
	}

	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> updateCliente(@PathVariable Long clienteId, @RequestBody Cliente clienteRequest) {
		Cliente cliente = null;
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		} else {
			Optional<Cliente> optional = clienteRepository.findById(clienteId);
			cliente = optional.get();
			updateCliente(cliente, clienteRequest);
			cadastroClienteService.salvar(cliente);
		}

		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("{clienteId}")
	public ResponseEntity<Void> deleteCliente(@PathVariable Long clienteId) {
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		} 
		
		cadastroClienteService.excluir(clienteId);
		return ResponseEntity.noContent().build();
	}

	private void updateCliente(Cliente cliente, Cliente clienteRequest) {
		if(clienteRequest.getNome() != null) {
			cliente.setNome(clienteRequest.getNome());
		}
		
		if(clienteRequest.getEmail() != null) {
			cliente.setEmail(clienteRequest.getEmail());
		}
		
		if(clienteRequest.getTelefone() != null) {
			cliente.setTelefone(clienteRequest.getTelefone());
		}
	}

}
