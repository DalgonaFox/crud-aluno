package br.com.aluno.dto;

import br.com.aluno.config.CursoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlunoDTORequest {

    private Long matricula;

    @NotBlank
    private String nomeAluno;

    @NotBlank
    @Column(unique = true)
    private String emailAluno;

    private CursoEnum curso;

    private Integer anoIngresso;

    private boolean status;
}
