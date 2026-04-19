package br.com.raizesdonordeste.api.service.cliente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record ClienteRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotBlank
        @CPF(message = "CPF em formato inválido")
        String cpf,

        @NotNull
        LocalDate dataNasc,

        Boolean aceiteLgpd,

        @NotBlank
        @Email(message = "Email em formato inválido")
        String login,

        @NotBlank
        String senha
) {
}
