package com.kaneko.helpdesk;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kaneko.helpdesk.domain.Chamado;
import com.kaneko.helpdesk.domain.Cliente;
import com.kaneko.helpdesk.domain.Tecnico;
import com.kaneko.helpdesk.domain.enums.Perfil;
import com.kaneko.helpdesk.domain.enums.Prioridade;
import com.kaneko.helpdesk.domain.enums.Status;
import com.kaneko.helpdesk.repositories.ChamadoRepository;
import com.kaneko.helpdesk.repositories.ClienteRepository;
import com.kaneko.helpdesk.repositories.TecnicoRepository;

@SpringBootApplication
public class HelpdeskApplication implements CommandLineRunner{
	@Autowired
	TecnicoRepository tecnicoRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	ChamadoRepository chamadoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Tecnico tec1 = new Tecnico(null, "Mauricio Kaneko", "26906383809","mauricio.kaneko@gmail.com","12345");
		tec1.addPerfil(Perfil.ADMIN);
		Cliente cli1 = new Cliente(null, "Elisa Yokomizo", "21688827897", "elisa.yokomizo@gmail.com", "98765");
		cli1.addPerfil(Perfil.CLIENTE);
		Chamado chm1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro Chamado",
				tec1, cli1);
		
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(chm1));
		
		
	}

}
