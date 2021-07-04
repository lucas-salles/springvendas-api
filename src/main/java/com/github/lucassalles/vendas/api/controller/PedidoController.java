package com.github.lucassalles.vendas.api.controller;

import com.github.lucassalles.vendas.api.dto.AtualizacaoStatusPedidoDTO;
import com.github.lucassalles.vendas.api.dto.InformacaoItemPedidoDTO;
import com.github.lucassalles.vendas.api.dto.InformacoesPedidoDTO;
import com.github.lucassalles.vendas.api.dto.PedidoDTO;
import com.github.lucassalles.vendas.domain.entity.ItemPedido;
import com.github.lucassalles.vendas.domain.entity.Pedido;
import com.github.lucassalles.vendas.domain.enums.StatusPedido;
import com.github.lucassalles.vendas.domain.service.PedidoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vendas/api/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salva um novo pedido")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pedido salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação"),
            @ApiResponse(code = 403, message = "Não autorizado")
    })
    public Integer create(@RequestBody @Valid PedidoDTO dto) {
        return pedidoService.save(dto).getId();
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um pedido")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pedido encontrado"),
            @ApiResponse(code = 404, message = "Pedido não encontrado para o ID informado"),
            @ApiResponse(code = 403, message = "Não autorizado")
    })
    public InformacoesPedidoDTO getById(@PathVariable("id") @ApiParam("Id do pedido") Integer id) {
        return pedidoService
                .obterPedidoCompleto(id)
                .map(pedido -> converter(pedido))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado."));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Atualiza o status de um pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Status do pedido atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado para o ID informado"),
            @ApiResponse(code = 403, message = "Não autorizado")
    })
    public void updateStatus(@PathVariable("id") @ApiParam("Id do pedido") Integer id,
                             @RequestBody AtualizacaoStatusPedidoDTO dto) {
        String novoStatus = dto.getNovoStatus();
        pedidoService.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }

    private InformacoesPedidoDTO converter(Pedido pedido) {
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .items(converter(pedido.getItens()))
                .build();
    }

    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens) {
        if(CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList();
        }

        return itens
                .stream()
                .map(item -> InformacaoItemPedidoDTO
                        .builder()
                        .descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
