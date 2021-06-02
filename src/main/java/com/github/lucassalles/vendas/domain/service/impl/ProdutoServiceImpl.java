package com.github.lucassalles.vendas.domain.service.impl;

import com.github.lucassalles.vendas.domain.entity.Produto;
import com.github.lucassalles.vendas.domain.repository.ProdutoRepository;
import com.github.lucassalles.vendas.domain.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoServiceImpl implements ProdutoService {
    @Autowired
    private ProdutoRepository repository;

    @Override
    @Transactional
    public Produto save(Produto produto) {
        return repository.save(produto);
    }

    @Override
    public List<Produto> findAll(Example example) {
        return repository.findAll();
    }

    @Override
    public Optional<Produto> findById(Integer id) {
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
