package com.kaneko.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaneko.helpdesk.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{

}
