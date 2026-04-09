package br.com.raizesdonordeste.api.infrastructure.exception;

import br.com.raizesdonordeste.api.infrastructure.exception.exceptions.PedidoSolicitacaoTrocoIndevida;
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

    @ExceptionHandler(PedidoSolicitacaoTrocoIndevida.class)
    public ProblemDetail handleSolicitacaoTrocoIndevido(PedidoSolicitacaoTrocoIndevida ex) {
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

}
