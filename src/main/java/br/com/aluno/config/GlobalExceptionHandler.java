package br.com.aluno.config;

import br.com.aluno.dto.ResponseException;
import br.com.aluno.exception.AlunoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseException> exception(Exception exceptionGeneric) {
        ResponseException responseException = new ResponseException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor");
        log.error("Exceção não tratada capturada: ", exceptionGeneric);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseException);
    }

    @ExceptionHandler(AlunoException.class)
    public ResponseEntity<ResponseException> alunoException(AlunoException alunoException) {
        String exceptionMessage = alunoException.getMessage();
        HttpStatus status = alunoException.getCodigoErro();
        ResponseException responseException = new ResponseException(status, exceptionMessage);
        log.error("Exceção não tratada capturada: ", alunoException);
        return ResponseEntity.status(status).body(responseException);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ResponseException> handlerMethodValidationException(HandlerMethodValidationException exception) {
        var exceptionMessage = exception.getAllValidationResults().get(0).getResolvableErrors().get(0).getDefaultMessage();
        ResponseException responseException = new ResponseException(HttpStatus.UNPROCESSABLE_ENTITY, exceptionMessage);
        log.error("Exceção não tratada capturada: {}", exceptionMessage);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(responseException);
    }

}