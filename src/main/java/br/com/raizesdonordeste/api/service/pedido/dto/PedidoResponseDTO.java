package br.com.raizesdonordeste.api.service.pedido.dto;

import br.com.raizesdonordeste.api.domain.pedido.Pedido;
import br.com.raizesdonordeste.api.domain.pedido.enums.CanalPedido;
import br.com.raizesdonordeste.api.domain.pedido.enums.StatusPedido;

import java.math.BigDecimal;
import java.util.List;

public record PedidoResponseDTO(
        Long id,
        CanalPedido canalPedido,
        BigDecimal valorTotal,
        StatusPedido statusPedido,
        List<ItemPedidoResponseDTO> itens) {

    public PedidoResponseDTO(Pedido pedido) {
        this(
                pedido.getId(),
                pedido.getCanalPedido(),
                pedido.getValorTotal(),
                pedido.getStatusPedido(),
                pedido.getItens()
                        .stream()
                        .map(ItemPedidoResponseDTO::new)
                        .toList());
    }
}
