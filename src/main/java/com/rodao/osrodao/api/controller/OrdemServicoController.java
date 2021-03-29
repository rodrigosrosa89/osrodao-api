package com.rodao.osrodao.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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

import com.rodao.osrodao.api.dto.OrdemServicoDto;
import com.rodao.osrodao.api.request.OrdemServicoRequest;
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
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoDto criar(@Valid @RequestBody OrdemServicoRequest ordemServicoRequest) {
		OrdemServico ordemServico = toEntity(ordemServicoRequest);
		return mapToDto(gestaoOrdemService.criar(ordemServico));
	}

	@GetMapping
	public List<OrdemServicoDto> listar() {
		return toCollectionDto(ordemServicoRepository.findAll());
	}
	
	@GetMapping("{ordemServicoId}")
	public ResponseEntity<OrdemServicoDto> buscar(@PathVariable Long ordemServicoId) {
		Optional<OrdemServico> ordem = ordemServicoRepository.findById(ordemServicoId);
		
		if(!ordem.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		OrdemServicoDto dto = mapToDto(ordem.get());
		return ResponseEntity.ok(dto);
	}
	
	private OrdemServicoDto mapToDto(OrdemServico ordemServico) {
		return modelMapper.map(ordemServico, OrdemServicoDto.class);
	}
	
	private List<OrdemServicoDto> toCollectionDto(List<OrdemServico> list) {
		return list.stream()
				.map(ordemServico -> mapToDto(ordemServico))
				.collect(Collectors.toList()); 
				
	}
	
	private OrdemServico toEntity(OrdemServicoRequest request) {
		return modelMapper.map(request, OrdemServico.class);
	}
}
