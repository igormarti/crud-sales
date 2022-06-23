package io.github.igormarti.service.impl;

import io.github.igormarti.domain.entity.ItemPedido;
import io.github.igormarti.domain.entity.Pedido;
import io.github.igormarti.domain.entity.Produto;
import io.github.igormarti.domain.repository.ItemsPedido;
import io.github.igormarti.domain.repository.Produtos;
import io.github.igormarti.exception.RegraNegocioException;
import io.github.igormarti.rest.dto.ItemPedidoDTO;
import io.github.igormarti.service.ItemsPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ItemsPedidoImpl implements ItemsPedidoService {

    @Autowired
    private Produtos produtosRepository;
    @Autowired
    private ItemsPedido repository;

    @Override
    public List<ItemPedido> save(List<ItemPedido> itensPedido) {

        repository.saveAll(itensPedido);

        return itensPedido;
    }

    @Override
    public List<ItemPedido> converteParaItemsPedido(Pedido pedido, List<ItemPedidoDTO> listaDto) {
        return listaDto.stream().map((item) -> {

            Produto produto = produtosRepository.findById(item.getProduto())
                    .orElseThrow(() ->
                            new RegraNegocioException("Produto inválido, código:"+item.getProduto()));

            return new ItemPedido(pedido, produto, item.getQuantidade());
        }).collect(Collectors.toList());
    }
}
