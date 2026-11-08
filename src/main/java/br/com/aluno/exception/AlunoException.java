package br.com.aluno.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AlunoException extends RuntimeException {

    HttpStatus codigoErro;

    public AlunoException(String message, HttpStatus codigoErro) {
        super(message);
        this.codigoErro = codigoErro;
    }
}
