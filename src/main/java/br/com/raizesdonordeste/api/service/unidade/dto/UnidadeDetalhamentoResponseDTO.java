package br.com.raizesdonordeste.api.service.unidade.dto;

import br.com.raizesdonordeste.api.domain.unidade.Unidade;

public record UnidadeDetalhamentoResponseDTO(
        Long id,
        String nome,
        String telefone
) {
    public UnidadeDetalhamentoResponseDTO(Unidade unidade) {
        this(unidade.getId(), unidade.getNome(), unidade.getTelefone());
    }
}
