package io.github.igormarti.rest.controller;

import io.github.igormarti.domain.entity.enums.StatusPedido;
import io.github.igormarti.rest.dto.AtualizaStatusPedidoDTO;
import io.github.igormarti.rest.dto.InfoPedidoCompletoDTO;
import io.github.igormarti.rest.dto.PedidoDTO;
import io.github.igormarti.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService pedidoService){
        service = pedidoService;
    }

    @PostMapping
    public ResponseEntity<Integer> save(@RequestBody  @Valid PedidoDTO dto){
        Integer pedido_id = service.save(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido_id)
                .toUri();

        return ResponseEntity.created(uri).body(pedido_id);
    }

    @GetMapping("{id}")
    public ResponseEntity<InfoPedidoCompletoDTO> findById(@PathVariable Integer id){
        InfoPedidoCompletoDTO pedido = service.findById(id);
        return ResponseEntity.ok(pedido);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Integer id,
            @RequestBody AtualizaStatusPedidoDTO dto
            ){

        service.updateStatus(id, StatusPedido.valueOf(dto.getStatusPedido()).toString());

        return ResponseEntity.noContent().build();
    }

}
