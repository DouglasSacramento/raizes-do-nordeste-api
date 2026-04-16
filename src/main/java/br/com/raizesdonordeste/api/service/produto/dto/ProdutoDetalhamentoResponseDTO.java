package br.com.raizesdonordeste.api.service.produto.dto;

import br.com.raizesdonordeste.api.domain.produto.Produto;

import java.math.BigDecimal;

public record ProdutoDetalhamentoResponseDTO(
        Long id,
        String nome,
        String descricao,
        BigDecimal precoAtual
) {
    public ProdutoDetalhamentoResponseDTO(Produto produto) {
        this(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPrecoAtual());
    }
}