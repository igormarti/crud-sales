package io.github.igormarti.rest.controller;

import io.github.igormarti.domain.entity.Cliente;
import io.github.igormarti.domain.repository.Clientes;
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
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/clientes")
public class ClienteController {

    @Autowired
    private Clientes clientes;

    @GetMapping("{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Integer id){
        Cliente cliente = clientes.findById(id)
                    .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado.") );
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<Cliente> save(@RequestBody @Valid Cliente cliente){
        Cliente clienteObj = clientes.save(cliente);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(clienteObj.getId()).toUri();

        return ResponseEntity.created(uri).body(clienteObj);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){

        clientes.findById(id)
                .map((cliente) -> {
                    clientes.delete(cliente);
                    return null;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado.") );
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid Cliente cliente){
        clientes.findById(id).map((clienteExistente) -> {
            cliente.setId(clienteExistente.getId());
            clientes.save(cliente);
            return null;
        }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado.") );
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> find(Cliente filtro) {

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);

        List<Cliente> listaclientes = clientes.findAll(example);

        return ResponseEntity.ok(listaclientes);
    }

}
