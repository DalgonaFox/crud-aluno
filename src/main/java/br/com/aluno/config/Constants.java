package br.com.aluno.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String MENSAGEM_ALUNO_NAO_ENCONTRADO = "Aluno não encontrado.";
    public static final String MENSAGEM_MATRICULA_DUPLICADA = "Matrícula Duplicada.";
    public static final String MENSAGEM_GENERICA = "Ocorreu um problema ao realizar a operação.";
    public static final String MENSAGEM_MATRICULA_TRANCADA = "Essa matrícula já está trancada.";
    public static final String MENSAGEM_MATRICULA_ABERTA = "Essa matrícula já está aberta.";
    public static final String MENSAGEM_CURSO_NAO_EXISTE = "Esse curso não está disponível.";
}
