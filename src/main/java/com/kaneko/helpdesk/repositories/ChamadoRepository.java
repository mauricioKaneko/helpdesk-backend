package com.kaneko.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaneko.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer>{

}
