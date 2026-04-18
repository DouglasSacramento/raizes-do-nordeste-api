package br.com.raizesdonordeste.api.infrastructure.exception.exceptions;

public class PagamentoRecusadoException extends RuntimeException {
    public PagamentoRecusadoException(String message) {
        super(message);
    }
}
