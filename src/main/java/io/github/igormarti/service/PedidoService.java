package io.github.igormarti.service;

import io.github.igormarti.rest.dto.InfoPedidoCompletoDTO;
import io.github.igormarti.rest.dto.PedidoDTO;
import org.springframework.transaction.annotation.Transactional;

public interface PedidoService {

    @Transactional
    Integer save(PedidoDTO dto);

    InfoPedidoCompletoDTO findById(Integer id);

    void updateStatus(Integer id, String dto);
}
