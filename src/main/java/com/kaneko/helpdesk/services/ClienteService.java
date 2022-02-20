package com.kaneko.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaneko.helpdesk.domain.Pessoa;
import com.kaneko.helpdesk.domain.Cliente;
import com.kaneko.helpdesk.domain.dtos.ClienteDTO;
import com.kaneko.helpdesk.repositories.PessoaRepository;
import com.kaneko.helpdesk.repositories.ClienteRepository;
import com.kaneko.helpdesk.services.exceptions.DataIntegrationViolationException;
import com.kaneko.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	PessoaRepository pessoaRepository;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado para o Id: " + id));
	}

	public List<Cliente> findAll() {
		
		return clienteRepository.findAll();
	}
	
	public Cliente create(ClienteDTO clienteDTO) {
		clienteDTO.setId(null);
		validaCPFeMail(clienteDTO);
		Cliente newCliente = new Cliente(clienteDTO);
		return clienteRepository.save(newCliente);
	}

	public Cliente update(Integer id, @Valid ClienteDTO clienteDTO) {
		clienteDTO.setId(id);
		Cliente oldCliente = findById(id);
		validaCPFeMail(clienteDTO);
		oldCliente = new Cliente(clienteDTO);
		
		return clienteRepository.save(oldCliente);
	}

	public void delete(Integer id) {
		Cliente cliente = findById(id);
		if(cliente.getChamados().size() > 0 ) {
			throw new DataIntegrationViolationException("Cliente possui ordens de serviço e não pode ser excluido");
		}
		clienteRepository.deleteById(id);
		
	}

	
	private void validaCPFeMail(ClienteDTO clienteDTO) {
		Optional<Pessoa> obj = pessoaRepository.findBycpf(clienteDTO.getCpf());
		if(obj.isPresent() && obj.get().getId() != clienteDTO.getId()) {
			throw new DataIntegrationViolationException("CPF já cadastrado no sistema");
		}
		
		obj = pessoaRepository.findByEmail(clienteDTO.getEmail());
		if(obj.isPresent() && obj.get().getId() != clienteDTO.getId()) {
			throw new DataIntegrationViolationException("E-mail já cadastrado no sistema");
		}
	}


}
