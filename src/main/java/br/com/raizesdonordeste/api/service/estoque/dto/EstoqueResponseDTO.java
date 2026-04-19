package br.com.raizesdonordeste.api.service.estoque.dto;

import br.com.raizesdonordeste.api.domain.unidade.ItemEstoque;

public record EstoqueResponseDTO(
        String unidade,
        String produto,
        Integer estoqueAtualizado
) {
    public EstoqueResponseDTO(ItemEstoque itemEstoque) {
        this(itemEstoque.getUnidade().getNome(), itemEstoque.getProduto().getNome(), itemEstoque.getQuantidade());
    }
}
