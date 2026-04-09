package br.com.raizesdonordeste.api.application.dto;

import br.com.raizesdonordeste.api.domain.pedido.Pedido;
import br.com.raizesdonordeste.api.domain.pedido.enums.StatusPedido;

import java.math.BigDecimal;

public record PedidoResponseDTO(Long id, BigDecimal valorTotal, StatusPedido statusPedido) {

    public PedidoResponseDTO(Pedido pedido) {
        this(pedido.getId(), pedido.getValorTotal(), pedido.getStatusPedido());
    }
}
