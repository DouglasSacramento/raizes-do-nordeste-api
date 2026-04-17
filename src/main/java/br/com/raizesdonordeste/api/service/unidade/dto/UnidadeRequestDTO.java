package br.com.raizesdonordeste.api.service.unidade.dto;

import jakarta.validation.constraints.NotBlank;

public record UnidadeRequestDTO(
        @NotBlank
        String nome,

        @NotBlank
        String telefone
) {
}
