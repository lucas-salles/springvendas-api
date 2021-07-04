package com.github.lucassalles.vendas.api.controller;

import com.github.lucassalles.vendas.domain.entity.Produto;
import com.github.lucassalles.vendas.domain.service.ProdutoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@RequestMapping("/vendas/api/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    @ApiOperation("Lista os produtos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Busca realizada com sucesso"),
            @ApiResponse(code = 403, message = "Não autorizado")
    })
    public List<Produto> find(Produto filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return produtoService.findAll(example);
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um produto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto encontrado"),
            @ApiResponse(code = 403, message = "Não autorizado"),
            @ApiResponse(code = 404, message = "Produto não encontrado para o ID informado")
    })
    public ResponseEntity<Produto> findById(@PathVariable("id") @ApiParam("Id do produto") Integer id) {
        Optional<Produto> opProduto = produtoService.findById(id);

        if (opProduto.isPresent()) {
            return ResponseEntity.ok(opProduto.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salva um novo produto")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Produto salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação"),
            @ApiResponse(code = 403, message = "Não autorizado")
    })
    public Produto create(@RequestBody @Valid Produto produto) {
        return produtoService.save(produto);
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualiza um produto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto atualizado com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação"),
            @ApiResponse(code = 403, message = "Não autorizado"),
            @ApiResponse(code = 404, message = "Produto não encontrado para o ID informado"),
    })
    public ResponseEntity<Produto> update(@PathVariable("id") @ApiParam("Id do produto") Integer id,
                                          @RequestBody @Valid Produto produto) {
        if (!produtoService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        produto.setId(id);
        produto = produtoService.save(produto);

        return ResponseEntity.ok(produto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Remove um produto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto removido com sucesso"),
            @ApiResponse(code = 403, message = "Não autorizado"),
            @ApiResponse(code = 404, message = "Produto não encontrado para o ID informado")
    })
    public ResponseEntity<Void> delete(@PathVariable("id") @ApiParam("Id do produto") Integer id) {
        if (!produtoService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        produtoService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
