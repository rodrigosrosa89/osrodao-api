package com.rodao.osrodao.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.rodao.osrodao.domain.exception.EntidadeNaoEncontradaException;
import com.rodao.osrodao.domain.exception.NegocioException;
import com.rodao.osrodao.domain.model.Cliente;
import com.rodao.osrodao.domain.model.Comentario;
import com.rodao.osrodao.domain.model.OrdemServico;
import com.rodao.osrodao.domain.model.StatusOrdemServicoEnum;
import com.rodao.osrodao.domain.repository.ClienteRepository;
import com.rodao.osrodao.domain.repository.ComentarioRepository;
import com.rodao.osrodao.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	public OrdemServico criar(@RequestBody OrdemServico ordemServico) {
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId()).orElseThrow(
				() -> new NegocioException("Cliente não encontrado"));

		ordemServico.setCliente(cliente != null ? cliente : null);
		ordemServico.setStatus(StatusOrdemServicoEnum.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		ordemServicoRepository.save(ordemServico);
		return ordemServico;
	}
	
	public void finalizarOrdemServico(Long ordemServicoId) {
		OrdemServico ordemServico =  buscarOrdemServico(ordemServicoId);
		ordemServico.finalizar();
		ordemServicoRepository.save(ordemServico);
	}

	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = buscarOrdemServico(ordemServicoId); 
		
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
			
		return comentarioRepository.save(comentario);
	}

	private OrdemServico buscarOrdemServico(Long ordemServicoId) {
		return ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem serviço não encontrada"));
	}
	

}
