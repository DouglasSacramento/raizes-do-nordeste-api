package br.com.raizesdonordeste.api.service.funcionario.dto;

import br.com.raizesdonordeste.api.domain.funcionario.Funcionario;

import java.time.LocalDateTime;

public record FuncionarioResponseDTO(
        Long id,
        String nome,
        String identificacao,
        String unidadeCadastro,
        LocalDateTime dataCadastro
) {
    public FuncionarioResponseDTO(Funcionario funcionario) {
        this(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getIdentificacao(),
                funcionario.getUsuario().getUnidade().getNome(),
                LocalDateTime.now());
    }
}
