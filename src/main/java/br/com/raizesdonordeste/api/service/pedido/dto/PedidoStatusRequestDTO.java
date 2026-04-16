package br.com.raizesdonordeste.api.application.service.pedido.dto;

import br.com.raizesdonordeste.api.domain.pedido.enums.StatusPedido;
import jakarta.validation.constraints.NotNull;

public record PedidoStatusRequestDTO(@NotNull StatusPedido novoStatus) {
}
