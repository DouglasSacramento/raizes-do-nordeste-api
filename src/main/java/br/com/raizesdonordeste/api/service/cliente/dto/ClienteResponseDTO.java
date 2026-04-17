package br.com.raizesdonordeste.api.service.cliente.dto;

import br.com.raizesdonordeste.api.domain.cliente.Cliente;

public record ClienteResponseDTO(
        Long id,
        String nome
) {
    public ClienteResponseDTO(Cliente cliente) {
        this(cliente.getId(), cliente.getNome());
    }
}
