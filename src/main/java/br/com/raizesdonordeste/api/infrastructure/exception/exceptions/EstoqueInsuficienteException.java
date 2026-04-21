package br.com.raizesdonordeste.api.infrastructure.exception.exceptions;

import br.com.raizesdonordeste.api.service.estoque.dto.ErroEstoqueDTO;

import java.util.List;

public class EstoqueInsuficienteException extends RuntimeException {

    private final List<ErroEstoqueDTO> erros;

    public EstoqueInsuficienteException(List<ErroEstoqueDTO> erros) {
        super("Estoque insuficiente");
        this.erros = erros;
    }

    public List<ErroEstoqueDTO> getErros() {
        return erros;
    }
}