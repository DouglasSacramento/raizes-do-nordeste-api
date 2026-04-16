package br.com.raizesdonordeste.api.application.service.dto;

import br.com.raizesdonordeste.api.domain.pedido.Pedido;
import br.com.raizesdonordeste.api.domain.pedido.enums.StatusPedido;

import java.math.BigDecimal;
import java.util.List;

public record PedidoResponseDTO(
        Long id,
        BigDecimal valorTotal,
        StatusPedido statusPedido,
        List<ItemPedidoResponseDTO> itens) {

    public PedidoResponseDTO(Pedido pedido) {
        this(
                pedido.getId(),
                pedido.getValorTotal(),
                pedido.getStatusPedido(),
                pedido.getItens()
                        .stream()
                        .map(ItemPedidoResponseDTO::new)
                        .toList());
    }
}
