package com.github.lucassalles.vendas.domain.service;

import com.github.lucassalles.vendas.api.dto.PedidoDTO;
import com.github.lucassalles.vendas.domain.entity.Pedido;
import com.github.lucassalles.vendas.domain.enums.StatusPedido;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Optional;

public interface PedidoService {
    Pedido save(PedidoDTO dto);
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
    List<Pedido> findAll(Example example);
    Optional<Pedido> findById(Integer id);
    void deleteById(Integer id);
    Boolean existsById(Integer id);
}
