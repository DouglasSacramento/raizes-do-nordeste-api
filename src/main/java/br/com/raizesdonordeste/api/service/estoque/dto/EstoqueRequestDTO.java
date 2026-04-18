package br.com.raizesdonordeste.api.service.estoque.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EstoqueRequestDTO(
        @NotNull
        Long unidadeId,

        @NotNull
        Long produtoId,

        @NotNull
        @Positive
        Integer quantidadeNova
) {
}
