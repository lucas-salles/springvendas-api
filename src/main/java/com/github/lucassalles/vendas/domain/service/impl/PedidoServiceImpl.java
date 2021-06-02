package com.github.lucassalles.vendas.domain.service.impl;

import com.github.lucassalles.vendas.api.dto.ItemPedidoDTO;
import com.github.lucassalles.vendas.api.dto.PedidoDTO;
import com.github.lucassalles.vendas.domain.entity.Cliente;
import com.github.lucassalles.vendas.domain.entity.ItemPedido;
import com.github.lucassalles.vendas.domain.entity.Pedido;
import com.github.lucassalles.vendas.domain.entity.Produto;
import com.github.lucassalles.vendas.domain.enums.StatusPedido;
import com.github.lucassalles.vendas.domain.repository.ClienteRepository;
import com.github.lucassalles.vendas.domain.repository.ItemPedidoRepository;
import com.github.lucassalles.vendas.domain.repository.PedidoRepository;
import com.github.lucassalles.vendas.domain.repository.ProdutoRepository;
import com.github.lucassalles.vendas.domain.service.PedidoService;
import com.github.lucassalles.vendas.exception.PedidoNaoEncontradoException;
import com.github.lucassalles.vendas.exception.RegraNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Override
    @Transactional
    public Pedido save(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clienteRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido: " + idCliente));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);

        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidoRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        pedidoRepository
                .findById(id)
                .map(pedido -> {
                    pedido.setStatus(statusPedido);
                    return pedidoRepository.save(pedido);
                })
                .orElseThrow(() -> new PedidoNaoEncontradoException());
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items) {
        if(items.isEmpty()) {
            throw new RegraNegocioException("Não é possível realizar pedido sem items.");
        }

        return items
                .stream()
                .map(dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtoRepository
                            .findById(idProduto)
                            .orElseThrow(() -> new RegraNegocioException("Código de produto inválido: " + idProduto));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);

                    return itemPedido;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Pedido> findAll(Example example) {
        return pedidoRepository.findAll();
    }

    @Override
    public Optional<Pedido> findById(Integer id) {
        return pedidoRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        pedidoRepository.deleteById(id);
    }

    @Override
    public Boolean existsById(Integer id) {
        return pedidoRepository.existsById(id);
    }
}
