package com.kaneko.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaneko.helpdesk.domain.Pessoa;
import com.kaneko.helpdesk.domain.Tecnico;
import com.kaneko.helpdesk.domain.dtos.TecnicoDTO;
import com.kaneko.helpdesk.repositories.PessoaRepository;
import com.kaneko.helpdesk.repositories.TecnicoRepository;
import com.kaneko.helpdesk.services.exceptions.DataIntegrationViolationException;
import com.kaneko.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {
	@Autowired
	TecnicoRepository tecnicoRepository;
	
	@Autowired
	PessoaRepository pessoaRepository;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado para o Id: " + id));
	}

	public List<Tecnico> findAll() {
		
		return tecnicoRepository.findAll();
	}
	
	public Tecnico create(TecnicoDTO tecnicoDTO) {
		tecnicoDTO.setId(null);
		validaCPFeMail(tecnicoDTO);
		Tecnico newTecnico = new Tecnico(tecnicoDTO);
		return tecnicoRepository.save(newTecnico);
	}

	public Tecnico update(Integer id, @Valid TecnicoDTO tecnicoDTO) {
		tecnicoDTO.setId(id);
		Tecnico oldTecnico = findById(id);
		validaCPFeMail(tecnicoDTO);
		oldTecnico = new Tecnico(tecnicoDTO);
		
		return tecnicoRepository.save(oldTecnico);
	}

	private void validaCPFeMail(TecnicoDTO tecnicoDTO) {
		Optional<Pessoa> obj = pessoaRepository.findBycpf(tecnicoDTO.getCpf());
		if(obj.isPresent() && obj.get().getId() != tecnicoDTO.getId()) {
			throw new DataIntegrationViolationException("CPF já cadastrado no sistema");
		}
		
		obj = pessoaRepository.findByEmail(tecnicoDTO.getEmail());
		if(obj.isPresent() && obj.get().getId() != tecnicoDTO.getId()) {
			throw new DataIntegrationViolationException("E-mail já cadastrado no sistema");
		}
	}

}
