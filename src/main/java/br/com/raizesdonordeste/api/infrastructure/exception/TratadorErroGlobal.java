package br.com.raizesdonordeste.api.infrastructure.exception;

import br.com.raizesdonordeste.api.infrastructure.exception.dto.ErroResponseDTO;
import br.com.raizesdonordeste.api.infrastructure.exception.exceptions.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class TratadorErroGlobal {

    @ExceptionHandler(PedidoSolicitacaoTrocoIndevidaException.class)
    public ResponseEntity<ErroResponseDTO> handleSolicitacaoTrocoIndevido(PedidoSolicitacaoTrocoIndevidaException ex, HttpServletRequest request) {
        var erro = new ErroResponseDTO(
                "BAD_REQUEST",
                ex.getMessage(),
                List.of(),
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponseDTO> handleDadosInvalidos(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> detalhes = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .collect(Collectors.toList());

        var erro = new ErroResponseDTO(
                "VALIDATION_ERROR",
                "Um ou mais campos da requisição estão inválidos.",
                detalhes,
                LocalDateTime.now(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(TransicaoStatusInvalidaException.class)
    public ResponseEntity<ErroResponseDTO> handleErrosDeMudancaDeStatusDePedidos(TransicaoStatusInvalidaException ex, HttpServletRequest request) {
        var erro = new ErroResponseDTO(
                "BAD_REQUEST",
                ex.getMessage(),
                List.of(),
                LocalDateTime.now(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroResponseDTO> handleRecursosNaoEncontrados(EntityNotFoundException ex, HttpServletRequest request) {
        var erro = new ErroResponseDTO(
                "NOT_FOUND",
                ex.getMessage(),
                List.of(),
                LocalDateTime.now(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(PagamentoStatusInvalidoException.class)
    public ResponseEntity<ErroResponseDTO> handlePagamentoComStatusInvalido(PagamentoStatusInvalidoException ex, HttpServletRequest request) {
        var erro = new ErroResponseDTO(
                "BAD_REQUEST",
                ex.getMessage(),
                List.of(),
                LocalDateTime.now(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(UsuarioNaoEncontrado.class)
    public ResponseEntity<ErroResponseDTO> handleUsuarioNaoEncontrado(UsuarioNaoEncontrado ex, HttpServletRequest request) {
        var erro = new ErroResponseDTO(
                "NOT_FOUND",
                ex.getMessage(),
                List.of(),
                LocalDateTime.now(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(RecursoJaCadastradoException.class)
    public ResponseEntity<ErroResponseDTO> handleRecursoJaCadastrado(RecursoJaCadastradoException ex, HttpServletRequest request) {
        var erro = new ErroResponseDTO(
                "BAD_REQUEST",
                ex.getMessage(),
                List.of(),
                LocalDateTime.now(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}
