package com.github.lucassalles.vendas.api.controller;

import com.github.lucassalles.vendas.domain.entity.Cliente;
import com.github.lucassalles.vendas.domain.service.ClienteService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vendas/api/clientes")
@Api("Api Clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @ApiOperation("Lista os clientes")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Busca realizada com sucesso"),
            @ApiResponse(code = 403, message = "Não autorizado")
    })
    public List<Cliente> find(Cliente filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return clienteService.findAll(example);
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado"),
            @ApiResponse(code = 403, message = "Não autorizado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado")
    })
    public ResponseEntity<Cliente> findById(@PathVariable("id") @ApiParam("Id do cliente") Integer id) {
        Optional<Cliente> opCliente = clienteService.findById(id);

        if (opCliente.isPresent()) {
            return ResponseEntity.ok(opCliente.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salva um novo cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação"),
            @ApiResponse(code = 403, message = "Não autorizado")
    })
    public Cliente create(@RequestBody @Valid Cliente cliente) {
        return clienteService.save(cliente);
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualiza um cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente atualizado com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação"),
            @ApiResponse(code = 403, message = "Não autorizado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado")
    })
    public ResponseEntity<Cliente> update(@PathVariable("id") @ApiParam("Id do cliente") Integer id,
                                          @RequestBody @Valid Cliente cliente) {
        if (!clienteService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        cliente.setId(id);
        cliente = clienteService.save(cliente);

        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Remove um cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente removido com sucesso"),
            @ApiResponse(code = 403, message = "Não autorizado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado")
    })
    public ResponseEntity<Void> delete(@PathVariable("id") @ApiParam("Id do cliente") Integer id) {
        if (!clienteService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        clienteService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
