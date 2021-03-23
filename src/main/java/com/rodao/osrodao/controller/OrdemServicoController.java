package com.rodao.osrodao.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rodao.osrodao.domain.model.OrdemServico;
import com.rodao.osrodao.domain.repository.OrdemServicoRepository;
import com.rodao.osrodao.domain.service.GestaoOrdemService;

@RestController
@RequestMapping("ordens-servico")
public class OrdemServicoController {
	
	@Autowired
	private GestaoOrdemService gestaoOrdemService;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<OrdemServico> criar(@Valid @RequestBody OrdemServico ordemServico) {
		return gestaoOrdemService.criar(ordemServico);
	}

	@GetMapping
	public List<OrdemServico> listar() {
		return ordemServicoRepository.findAll();
	}
	
	@GetMapping("{ordemServicoId}")
	public ResponseEntity<OrdemServico> buscar(@PathVariable Long ordemServicoId) {
		Optional<OrdemServico> ordem = ordemServicoRepository.findById(ordemServicoId);
		
		if(!ordem.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(ordem.get());
	}
}
