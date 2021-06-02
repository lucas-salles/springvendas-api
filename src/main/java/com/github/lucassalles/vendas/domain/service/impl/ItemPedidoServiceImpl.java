package com.github.lucassalles.vendas.domain.service.impl;

import com.github.lucassalles.vendas.domain.entity.ItemPedido;
import com.github.lucassalles.vendas.domain.repository.ItemPedidoRepository;
import com.github.lucassalles.vendas.domain.service.ItemPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemPedidoServiceImpl implements ItemPedidoService {
    @Autowired
    private ItemPedidoRepository repository;

    @Override
    @Transactional
    public ItemPedido save(ItemPedido itemsPedido) {
        return repository.save(itemsPedido);
    }

    @Override
    public List<ItemPedido> findAll(Example example) {
        return repository.findAll();
    }

    @Override
    public Optional<ItemPedido> findById(Integer id) {
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
