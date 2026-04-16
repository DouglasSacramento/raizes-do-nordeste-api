package br.com.raizesdonordeste.api.service.pedido.dto;

import br.com.raizesdonordeste.api.domain.pedido.Pedido;
import br.com.raizesdonordeste.api.domain.pedido.enums.StatusPedido;

public record PedidoStatusResponseDTO(StatusPedido statusPedido, Long id) {

    public PedidoStatusResponseDTO(Pedido pedido) {
        this(pedido.getStatusPedido(), pedido.getId());
    }
}
