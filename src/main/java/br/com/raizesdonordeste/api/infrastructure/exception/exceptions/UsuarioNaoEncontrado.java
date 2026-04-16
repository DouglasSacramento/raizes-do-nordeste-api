package br.com.raizesdonordeste.api.infrastructure.exception.exceptions;

public class UsuarioNaoEncontrado extends RuntimeException {
    public UsuarioNaoEncontrado(String message) {
        super(message);
    }
}
