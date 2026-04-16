package br.com.raizesdonordeste.api.application.service.pedido.dto;

import br.com.raizesdonordeste.api.domain.pedido.ItemPedido;

import java.math.BigDecimal;

public record ItemPedidoResponseDTO(
        Long id,
        Integer quantidade,
        BigDecimal precoUnitario,
        Long produtoId,
        String produtoNome
) {
    public ItemPedidoResponseDTO(ItemPedido item) {
        this(
                item.getId(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getProduto().getId(),
                item.getProduto().getNome());
    }
}
