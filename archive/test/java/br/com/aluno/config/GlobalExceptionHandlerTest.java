package br.com.aluno.config;

import br.com.aluno.exception.AlunoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;

import static br.com.aluno.config.Constants.MENSAGEM_GENERICA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @ParameterizedTest
    @EnumSource(value = HttpStatus.class, names = {"INTERNAL_SERVER_ERROR", "UNPROCESSABLE_ENTITY", "NOT_FOUND"})
    void alunoNaoEncontradoException(HttpStatus status) {
        AlunoException ex = new AlunoException(MENSAGEM_GENERICA, status);

        var response = handler.alunoException(ex);

        Assertions.assertNotNull(response.getBody());
        assertEquals(status, response.getBody().getStatus());
    }

    @Test
    void exceptionGeneric() {
        Exception ex = mock(Exception.class);

        var response = handler.exception(ex);

        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals("Erro interno do servidor", response.getBody().getMessage());
    }

    @Test
    void handlerMethodValidationException() {
        HandlerMethodValidationException ex = mock(HandlerMethodValidationException.class);
        ParameterValidationResult paramResult = mock(ParameterValidationResult.class);
        org.springframework.context.MessageSourceResolvable resolvableError = mock(org.springframework.context.MessageSourceResolvable.class);

        when(ex.getParameterValidationResults()).thenReturn(List.of(paramResult));
        when(paramResult.getResolvableErrors()).thenReturn(List.of(resolvableError));
        when(resolvableError.getDefaultMessage()).thenReturn("campo inválido");

        var response = handler.handlerMethodValidationException(ex);

        assertEquals(UNPROCESSABLE_ENTITY, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals(ex.getParameterValidationResults().get(0).getResolvableErrors().get(0).getDefaultMessage(), response.getBody().getMessage());
    }
}
