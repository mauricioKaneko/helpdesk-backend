package com.kaneko.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaneko.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
