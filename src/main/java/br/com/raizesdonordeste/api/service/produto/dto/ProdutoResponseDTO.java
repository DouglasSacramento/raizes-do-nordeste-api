package br.com.raizesdonordeste.api.service.produto.dto;

import br.com.raizesdonordeste.api.domain.produto.Produto;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        BigDecimal precoAtual
) {
    public ProdutoResponseDTO(Produto produto) {
        this(produto.getId(), produto.getNome(), produto.getPrecoAtual());
    }
}
