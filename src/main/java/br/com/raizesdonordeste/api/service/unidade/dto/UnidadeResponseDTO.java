package br.com.raizesdonordeste.api.service.unidade.dto;

import br.com.raizesdonordeste.api.domain.unidade.Unidade;

public record UnidadeResponseDTO(
        Long id,
        String nome
) {
    public UnidadeResponseDTO(Unidade unidade) {
        this(unidade.getId(), unidade.getNome());
    }
}
