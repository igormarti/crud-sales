package io.github.igormarti.service;

import io.github.igormarti.domain.entity.ItemPedido;
import io.github.igormarti.domain.entity.Pedido;
import io.github.igormarti.rest.dto.ItemPedidoDTO;

import java.util.List;
import java.util.Set;

public interface ItemsPedidoService {

    List<ItemPedido> save(List<ItemPedido> itensPedido);
    List<ItemPedido> converteParaItemsPedido(Pedido pedido, List<ItemPedidoDTO> listaDto);
}
