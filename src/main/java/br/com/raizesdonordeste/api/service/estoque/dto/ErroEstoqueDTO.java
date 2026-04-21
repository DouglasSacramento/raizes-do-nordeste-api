package br.com.raizesdonordeste.api.service.estoque.dto;

public record ErroEstoqueDTO(
        Long produtoId,
        String nome,
        int solicitado,
        int disponivel
) {
}
