package br.com.raizesdonordeste.api.infrastructure.exception;

import br.com.raizesdonordeste.api.infrastructure.exception.exceptions.TransicaoStatusInvalidaException;
import br.com.raizesdonordeste.api.infrastructure.exception.exceptions.PedidoSolicitacaoTrocoIndevidaException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class TratadorErroGlobal {

    @ExceptionHandler(PedidoSolicitacaoTrocoIndevidaException.class)
    public ProblemDetail handleSolicitacaoTrocoIndevido(PedidoSolicitacaoTrocoIndevidaException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setDetail(ex.getMessage());
        problem.setTitle("Solicitação Troco Indevida");
        problem.setProperty("dataHora", LocalDateTime.now());

        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleDadosInvalidos(MethodArgumentNotValidException ex) {
        String mensagem = "Um ou mais campos da requisição estão inválidos.";

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setDetail(mensagem);
        problem.setTitle("Dados Inválidos");
        problem.setProperty("dataHora", LocalDateTime.now());

        Map<String, String> camposComErro = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        erro -> erro.getField(),
                        erro -> erro.getDefaultMessage(),
                        (mensagemAntiga, mensagemNova)
                                -> mensagemAntiga + " e " + mensagemNova
                ));
        problem.setProperty("campoInvalido", camposComErro);

        return problem;
    }

    @ExceptionHandler(TransicaoStatusInvalidaException.class)
    public ProblemDetail handleErrosDeMudancaDeStatusDePedidos(TransicaoStatusInvalidaException ex){
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Transição status inválida");
        problem.setDetail(ex.getMessage());
        problem.setProperty("dataHora", LocalDateTime.now());

        return problem;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleRecursosNaoEncontrados(EntityNotFoundException ex){
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Recurso não encontrado");
        problem.setDetail(ex.getMessage());
        problem.setProperty("dataHora", LocalDateTime.now());

        return problem;
    }
}
