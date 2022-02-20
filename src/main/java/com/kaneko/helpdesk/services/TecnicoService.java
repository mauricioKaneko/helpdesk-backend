package com.kaneko.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaneko.helpdesk.domain.Tecnico;
import com.kaneko.helpdesk.domain.dtos.TecnicoDTO;
import com.kaneko.helpdesk.repositories.TecnicoRepository;
import com.kaneko.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {
	@Autowired
	TecnicoRepository tecnicoRepository;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado para o Id: " + id));
	}

	public List<Tecnico> findAll() {
		
		return tecnicoRepository.findAll();
	}
	
	public Tecnico create(TecnicoDTO tecnicoDTO) {
		tecnicoDTO.setId(null);
		Tecnico newTecnico = new Tecnico(tecnicoDTO);
		return tecnicoRepository.save(newTecnico);
	}
}
