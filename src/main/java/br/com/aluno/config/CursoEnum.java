package br.com.aluno.config;

import br.com.aluno.exception.AlunoException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static br.com.aluno.config.Constants.MENSAGEM_CURSO_NAO_EXISTE;

@AllArgsConstructor
public enum CursoEnum {
    ADMINISTRACAO("Administração"),
    LOGISTICA("Logística"),
    ANALISE_E_DESENVOLVIMENTO_DE_SISTEMAS("Análise e Desenvolvimento de Sistemas");

    private final String descricao;

    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static CursoEnum fromValue(String curso) {
        for (CursoEnum c : values()) {
            if(c.name().equalsIgnoreCase(curso) || c.getDescricao().equalsIgnoreCase(curso))
                return c;
        }
        throw new AlunoException(MENSAGEM_CURSO_NAO_EXISTE, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
