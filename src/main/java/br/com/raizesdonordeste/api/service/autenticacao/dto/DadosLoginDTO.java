package br.com.raizesdonordeste.api.service.autenticacao.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosLoginDTO(
        @NotBlank
        String login,

        @NotBlank
        String senha
) {
}
