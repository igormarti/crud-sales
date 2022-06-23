package io.github.igormarti.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoItemPedidoDTO {

    private String nomeProduto;
    private Integer quantidade;
}
