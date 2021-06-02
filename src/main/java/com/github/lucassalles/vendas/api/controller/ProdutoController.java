package com.github.lucassalles.vendas.api.controller;

import com.github.lucassalles.vendas.domain.entity.Produto;
import com.github.lucassalles.vendas.domain.service.ProdutoService;
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
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<Produto> find(Produto filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return produtoService.findAll(example);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> findById(@PathVariable("id") Integer id) {
        Optional<Produto> opProduto = produtoService.findById(id);

        if (opProduto.isPresent()) {
            return ResponseEntity.ok(opProduto.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto create(@RequestBody @Valid Produto produto) {
        return produtoService.save(produto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(@PathVariable("id") Integer id, @RequestBody @Valid Produto produto) {
        if (!produtoService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        produto.setId(id);
        produto = produtoService.save(produto);

        return ResponseEntity.ok(produto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        if (!produtoService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        produtoService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
