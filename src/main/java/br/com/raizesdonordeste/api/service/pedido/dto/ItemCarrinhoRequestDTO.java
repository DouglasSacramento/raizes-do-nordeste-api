package br.com.raizesdonordeste.api.application.service.pedido.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemCarrinhoRequestDTO(
        @NotNull
        Long produtoId,

        @NotNull
        @Min(1)
        Integer quantidade
) {
}
