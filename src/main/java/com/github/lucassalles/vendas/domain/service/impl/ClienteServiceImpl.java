package com.github.lucassalles.vendas.domain.service.impl;

import com.github.lucassalles.vendas.domain.entity.Cliente;
import com.github.lucassalles.vendas.domain.repository.ClienteRepository;
import com.github.lucassalles.vendas.domain.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository repository;

    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        return repository.save(cliente);
    }

    @Override
    public List<Cliente> findAll(Example example) {
        return repository.findAll();
    }

    @Override
    public Optional<Cliente> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Boolean existsById(Integer id) {
        return repository.existsById(id);
    }
}
