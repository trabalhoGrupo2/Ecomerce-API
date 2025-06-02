package org.serratec.h2.grupo2.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	//QUANDO A FALHAS EM VALIDAÇÕES DO TIPO @VALID, HTTP 400
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatusCode status,
                                                                   WebRequest request) {
        List<String> erros = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ErroResposta erroResposta = new ErroResposta(
                status.value(),
                "Existem campos inválidos, confira o preenchimento.",
                LocalDateTime.now(),
                erros,
                request.getDescription(false).replace("uri=", "") // extrai o caminho
        );

        return handleExceptionInternal(ex, erroResposta, headers, status, request);
    }

    //QUANDO A ENTIDADE NÃO FOI ENCONTRADA PELO ID, HTTP 404
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroResposta> handleEntidadeNaoEncontrada(EntityNotFoundException ex, HttpServletRequest request) {
        ErroResposta erro = new ErroResposta(
                HttpStatus.NOT_FOUND.value(),
                "Não foi encontrado nenhum resultado com o ID informado.",
                LocalDateTime.now(),
                List.of(ex.getMessage()),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    //QUANDO O JSON ESTÁ MAL FORMATADO, HTTP 400
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResposta> handleJsonInvalido(HttpMessageNotReadableException ex, HttpServletRequest request) {
        ErroResposta erro = new ErroResposta(
                HttpStatus.BAD_REQUEST.value(),
                "Requisição inválida",
                LocalDateTime.now(),
                List.of("Formato JSON inválido ou campo mal preenchido."),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

      //QUANDO APARECE QUALQUER ERRO INESPERADO
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> handleErroInterno(Exception ex, HttpServletRequest request) {
        ErroResposta erro = new ErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro inesperado no servidor",
                LocalDateTime.now(),
                List.of(ex.getMessage()),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}