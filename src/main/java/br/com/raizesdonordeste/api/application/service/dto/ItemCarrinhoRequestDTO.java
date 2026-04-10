package br.com.raizesdonordeste.api.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemCarrinhoRequestDTO(
        @NotNull
        Long produtoId,

        @NotNull
        @Min(1)
        Integer quantidade
) {
}
