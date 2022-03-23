package com.kaneko.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado para o Id: " + id));
	}

	public List<Tecnico> findAll() {
		
		return tecnicoRepository.findAll();
	}
	
	public Tecnico create(TecnicoDTO tecnicoDTO) {
		tecnicoDTO.setId(null);
		tecnicoDTO.setSenha(encoder.encode(tecnicoDTO.getSenha()));
		validaCPFeMail(tecnicoDTO);
		Tecnico newTecnico = new Tecnico(tecnicoDTO);
		return tecnicoRepository.save(newTecnico);
	}

	public Tecnico update(Integer id, @Valid TecnicoDTO tecnicoDTO) {
		tecnicoDTO.setId(id);
		Tecnico oldTecnico = findById(id);
		
		if(!tecnicoDTO.getSenha().equals(oldTecnico.getSenha())) {
			tecnicoDTO.setSenha(encoder.encode(tecnicoDTO.getSenha()));
		}
		
		validaCPFeMail(tecnicoDTO);
		oldTecnico = new Tecnico(tecnicoDTO);
		
		return tecnicoRepository.save(oldTecnico);
	}

	public void delete(Integer id) {
		Tecnico tecnico = findById(id);
		if(tecnico.getChamados().size() > 0 ) {
			throw new DataIntegrationViolationException("Tecnico possui ordens de serviço e não pode ser excluido");
		}
		tecnicoRepository.deleteById(id);
		
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
