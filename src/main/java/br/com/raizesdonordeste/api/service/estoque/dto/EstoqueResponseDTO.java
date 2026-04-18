package br.com.raizesdonordeste.api.service.estoque.dto;

import br.com.raizesdonordeste.api.domain.unidade.ItemEstoque;

public record EstoqueResponseDTO(
        Integer estoqueAtualizado
) {
    public EstoqueResponseDTO(ItemEstoque itemEstoque) {
        this(itemEstoque.getQuantidade());
    }
}
