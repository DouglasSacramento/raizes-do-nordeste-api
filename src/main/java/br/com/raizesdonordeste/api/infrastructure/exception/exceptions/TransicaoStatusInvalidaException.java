package br.com.raizesdonordeste.api.infrastructure.exception.exceptions;

public class TransicaoStatusInvalidaException extends RuntimeException {
    public TransicaoStatusInvalidaException(String message) {
        super(message);
    }
}
