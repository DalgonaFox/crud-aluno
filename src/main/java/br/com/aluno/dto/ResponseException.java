package br.com.aluno.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ResponseException {

    private HttpStatus status;
    private String message;

}
