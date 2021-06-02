package com.github.lucassalles.vendas.domain.service;

import com.github.lucassalles.vendas.domain.entity.ItemPedido;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Optional;

public interface ItemPedidoService {
    ItemPedido save(ItemPedido itemsPedido);
    List<ItemPedido> findAll(Example example);
    Optional<ItemPedido> findById(Integer id);
    void deleteById(Integer id);
    Boolean existsById(Integer id);
}
