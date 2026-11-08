package br.com.aluno.dto;

import br.com.aluno.config.CursoEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class AlunoDTOResponse {

    private Long matricula;
    private String nomeAluno;
    private String emailAluno;
    private String curso;

    private Integer anoIngresso;

    private String status;

    public AlunoDTOResponse(Long matricula, String nomeAluno, String emailAluno, String curso, Integer anoIngresso, String status) {
        this.matricula = matricula;
        this.nomeAluno = nomeAluno;
        this.emailAluno = emailAluno;
        this.curso = curso;
        this.anoIngresso = anoIngresso;
        this.status = status;
    }
}
