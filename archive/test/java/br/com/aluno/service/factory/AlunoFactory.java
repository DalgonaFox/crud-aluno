package br.com.aluno.service.factory;

import br.com.aluno.dto.AlunoDTORequest;
import br.com.aluno.entity.Alunos;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class AlunoFactory {

    public static @NotNull AlunoDTORequest criarAlunoDTO() {
        return AlunoDTORequest.builder()
                .nomeAluno("João Silva")
                .cpf("12345678901")
                .dataNascimento(LocalDate.of(2000, 5, 15))
                .emailAluno("joao.silva@email.com")
                .telefoneAluno("11987654321")
                .curso("Engenharia de Software")
                .turma("Turma A")
                .dataCadastro(LocalDate.now())
                .build();
    }

    public static @NotNull AlunoDTORequest criarAlunoNuloDTO() {
        return AlunoDTORequest.builder().build();
    }

    public static @NotNull Alunos criarAlunoEntity() {
        return Alunos.builder()
                .matricula(1L)
                .nomeAluno("João Silva")
                .cpf("12345678901")
                .dataNascimento(LocalDate.of(2000, 5, 15))
                .emailAluno("joao.silva@email.com")
                .telefoneAluno("11987654321")
                .curso("Engenharia de Software")
                .turma("Turma A")
                .dataCadastro(LocalDate.now())
                .build();
    }

    public static @NotNull AlunoDTORequest atualizarAlunoDTO() {
        return AlunoDTORequest.builder()
                .nomeAluno("João Silva Atualizado")
                .cpf("10987654321")
                .dataNascimento(LocalDate.of(2000, 5, 15))
                .emailAluno("joao.atualizado@email.com")
                .telefoneAluno("11912345678")
                .curso("Ciência da Computação")
                .turma("Turma B")
                .dataCadastro(LocalDate.now())
                .build();
    }
}