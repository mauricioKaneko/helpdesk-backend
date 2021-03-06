package com.kaneko.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaneko.helpdesk.domain.Chamado;
import com.kaneko.helpdesk.domain.Cliente;
import com.kaneko.helpdesk.domain.Tecnico;
import com.kaneko.helpdesk.domain.enums.Perfil;
import com.kaneko.helpdesk.domain.enums.Prioridade;
import com.kaneko.helpdesk.domain.enums.Status;
import com.kaneko.helpdesk.repositories.ChamadoRepository;
import com.kaneko.helpdesk.repositories.ClienteRepository;
import com.kaneko.helpdesk.repositories.TecnicoRepository;

@Service
public class DBService {
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ChamadoRepository chamadoRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public void instanciaDB() {
		Tecnico tec1 = new Tecnico(null, "Mauricio", "022.856.928-11","mauricio.kaneko@gmail.com",encoder.encode("12345"));
		tec1.addPerfil(Perfil.TECNICO);
		Tecnico tec2 = new Tecnico(null, "Kendi", "269.063.838-09","kendi.kaneko@gmail.com",encoder.encode("12345"));
		tec2.addPerfil(Perfil.TECNICO);
		Tecnico tec3 = new Tecnico(null, "Kaneko", "588.025.700-27","kaneko.kaneko@gmail.com",encoder.encode("12345"));
		tec3.addPerfil(Perfil.TECNICO);

		Cliente cli1 = new Cliente(null, "Elisa Yokomizo", "216.888.278-97", "elisa.yokomizo@gmail.com", encoder.encode("12345"));
		cli1.addPerfil(Perfil.CLIENTE);
		Chamado chm1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro Chamado",
				tec1, cli1);
		
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		tecnicoRepository.saveAll(Arrays.asList(tec2));
		tecnicoRepository.saveAll(Arrays.asList(tec3));
		clienteRepository.saveAll(Arrays.asList(cli1));  
		chamadoRepository.saveAll(Arrays.asList(chm1));
	}
}
