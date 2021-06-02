package com.github.lucassalles.vendas.domain.service;

import com.github.lucassalles.vendas.domain.entity.Produto;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Optional;

public interface ProdutoService {
    Produto save(Produto produto);
    List<Produto> findAll(Example example);
    Optional<Produto> findById(Integer id);
    void deleteById(Integer id);
    Boolean existsById(Integer id);
}
