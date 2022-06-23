package io.github.igormarti.rest.controller;

import io.github.igormarti.domain.entity.Produto;
import io.github.igormarti.domain.repository.Produtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/produtos")
public class ProdutoController {

    @Autowired
    private Produtos produtos;

    @GetMapping("{id}")
    public ResponseEntity<Produto> findById(@PathVariable Integer id){
        Produto produto = produtos.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado") );

        return ResponseEntity.ok(produto);
    }

    @PostMapping
    public ResponseEntity<Produto> save(@RequestBody @Valid Produto produto){
        Produto produtoObj = produtos.save(produto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(produtoObj.getId()).toUri();

        return ResponseEntity.created(uri).body(produtoObj);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        produtos.findById(id).map((p) -> {
            produtos.delete(p);
            return null;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não encontrado."));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid Produto produto) {
        produtos.findById(id).map((p) -> {
            produto.setId(p.getId());
            produtos.save(produto);
            return null;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @GetMapping
    public ResponseEntity<List<Produto>> find(Produto filtro){

        ExampleMatcher matcher = ExampleMatcher.matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);

        List<Produto> listaProdutos = produtos.findAll(example);

        return ResponseEntity.ok(listaProdutos);
    }

}
