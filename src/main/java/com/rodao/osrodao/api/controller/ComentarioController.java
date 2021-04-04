package com.rodao.osrodao.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rodao.osrodao.api.dto.ComentarioDto;
import com.rodao.osrodao.api.request.ComentarioRequest;
import com.rodao.osrodao.domain.exception.EntidadeNaoEncontradaException;
import com.rodao.osrodao.domain.model.Comentario;
import com.rodao.osrodao.domain.model.OrdemServico;
import com.rodao.osrodao.domain.repository.OrdemServicoRepository;
import com.rodao.osrodao.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemService;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	
	@GetMapping
	public List<ComentarioDto> listar(@PathVariable Long ordemServicoId) {
		OrdemServico os = ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));	
		
		return toCollectionDto(os.getComentarios());
	}
	

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioDto adicionar(@PathVariable Long ordemServicoId, @Valid @RequestBody ComentarioRequest request) {
		Comentario comentario = gestaoOrdemService.adicionarComentario(ordemServicoId, request.getDescricao());
		
		return toDto(comentario);
		
	}
	
	private ComentarioDto toDto(Comentario comentario) {
		return modelMapper.map(comentario, ComentarioDto.class);
	}
	
	private List<ComentarioDto> toCollectionDto(List<Comentario> comentarios) {
		return comentarios.stream()
				.map(comentario -> toDto(comentario))
				.collect(Collectors.toList()); 
	}


}
