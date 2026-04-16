package br.com.raizesdonordeste.api.service.produto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        @NotBlank
        String nome,

        @NotBlank
        String descricao,

        @NotNull
        @Positive
        BigDecimal precoAtual
) {
}
