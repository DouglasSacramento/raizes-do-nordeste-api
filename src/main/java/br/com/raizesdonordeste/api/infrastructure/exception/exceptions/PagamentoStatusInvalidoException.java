package br.com.raizesdonordeste.api.infrastructure.exception.exceptions;

public class PagamentoStatusInvalidoException extends RuntimeException {
    public PagamentoStatusInvalidoException(String message) {
        super(message);
    }
}
