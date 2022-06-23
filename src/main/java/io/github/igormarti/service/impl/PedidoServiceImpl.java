package io.github.igormarti.service.impl;

import io.github.igormarti.domain.entity.Cliente;
import io.github.igormarti.domain.entity.ItemPedido;
import io.github.igormarti.domain.entity.Pedido;
import io.github.igormarti.domain.entity.enums.StatusPedido;
import io.github.igormarti.domain.repository.Clientes;
import io.github.igormarti.domain.repository.Pedidos;
import io.github.igormarti.exception.NaoEncontradoException;
import io.github.igormarti.exception.RegraNegocioException;
import io.github.igormarti.rest.dto.InfoItemPedidoDTO;
import io.github.igormarti.rest.dto.InfoPedidoCompletoDTO;
import io.github.igormarti.rest.dto.PedidoDTO;
import io.github.igormarti.service.ItemsPedidoService;
import io.github.igormarti.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final ItemsPedidoService itemsPedidoService;

    @Override
    public Integer save(PedidoDTO dto) {
        // Obtém cliente pelo id
        Cliente cliente = clientesRepository.findById(dto.getCliente())
                .orElseThrow(() -> new RegraNegocioException("Cliente inválido"));

        Pedido pedido = new Pedido(cliente, LocalDate.now(), dto.getTotal());

        List<ItemPedido> itensPedido = itemsPedidoService.converteParaItemsPedido(pedido,dto.getItems());

        repository.save(pedido);
        itemsPedidoService.save(itensPedido);
        pedido.getItens().addAll(itensPedido);

        return pedido.getId();
    }

    @Override
    public InfoPedidoCompletoDTO findById(Integer id) {
        return repository.findByIdFetchItens(id).map((pedido) -> converter(pedido))
                .orElseThrow(() ->
                        new NaoEncontradoException("Pedido não encontrado, código:"+id));
    }
    @Transactional
    @Override
    public void updateStatus(Integer id, String novoStatus) {
       repository.findById(id).map(pedido -> {
           pedido.setStatus(StatusPedido.valueOf(novoStatus));
           repository.save(pedido);
           return pedido;
       }).orElseThrow(() -> new NaoEncontradoException("Pedido não encontrado."));

    }

    private InfoPedidoCompletoDTO converter(Pedido pedido){
        return InfoPedidoCompletoDTO.builder()
                .nomeCliente(pedido.getCliente().getNome())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .itens(converter(pedido.getItens()))
                .build();
    }

    private List<InfoItemPedidoDTO> converter(List<ItemPedido> itens){
        return itens.stream().map((item) -> {
            return InfoItemPedidoDTO.builder()
                    .nomeProduto(item.getProduto().getDescricao())
                    .quantidade(item.getQuantidade())
                    .build();
        }).collect(Collectors.toList());
    }
}
