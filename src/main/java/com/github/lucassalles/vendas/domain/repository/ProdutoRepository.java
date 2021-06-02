package com.github.lucassalles.vendas.domain.repository;

import com.github.lucassalles.vendas.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
