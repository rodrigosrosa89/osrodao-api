package com.rodao.osrodao.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.rodao.osrodao.domain.exception.NegocioException;
import com.rodao.osrodao.domain.model.Cliente;
import com.rodao.osrodao.domain.model.OrdemServico;
import com.rodao.osrodao.domain.model.StatusOrdemServicoEnum;
import com.rodao.osrodao.domain.repository.ClienteRepository;
import com.rodao.osrodao.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemService {
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public OrdemServico criar(@RequestBody OrdemServico ordemServico) {
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId()).orElseThrow(
				() -> new NegocioException("Cliente não encontrado"));

		ordemServico.setCliente(cliente != null ? cliente : null);
		ordemServico.setStatus(StatusOrdemServicoEnum.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		ordemServicoRepository.save(ordemServico);
		return ordemServico;
	}

}
