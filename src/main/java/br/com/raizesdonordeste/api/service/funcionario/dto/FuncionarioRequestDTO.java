package br.com.raizesdonordeste.api.service.funcionario.dto;

import br.com.raizesdonordeste.api.domain.unidade.Unidade;
import br.com.raizesdonordeste.api.domain.usuario.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record FuncionarioRequestDTO(
        @NotBlank
        String nome,

        @NotBlank
        @CPF
        String cpf,

        @NotNull
        LocalDate dataNasc,

        @NotNull
        LocalDate dataAdmissao,

        @NotBlank
        @Email
        String login,

        @NotBlank
        String senha,

        @NotNull
        Role role,

        @NotNull
        Long unidade,

        Boolean aceiteLgpd
) {
}
