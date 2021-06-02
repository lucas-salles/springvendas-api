package com.github.lucassalles.vendas.domain.service;

import com.github.lucassalles.vendas.domain.entity.Cliente;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    Cliente save(Cliente cliente);
    List<Cliente> findAll(Example example);
    Optional<Cliente> findById(Integer id);
    void deleteById(Integer id);
    Boolean existsById(Integer id);
}
