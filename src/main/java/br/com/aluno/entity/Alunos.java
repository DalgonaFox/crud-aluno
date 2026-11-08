package br.com.aluno.entity;

import br.com.aluno.config.CursoEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.Year;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Aluno")
public class Alunos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matricula;

    @NotBlank
    @NotNull
    private String nomeAluno;

    @NotBlank
    @NotNull
    @Column(unique = true)
    @Email(message = "Formato inválido de e-mail.")
    private String emailAluno;

    @Enumerated(EnumType.STRING)
    private CursoEnum curso;

    @Min(value = 2000, message = "O ano não pode ser anterior ao ano 2000.")
    @Max(value = 2026, message = "O ano não pode ultrapassar o ano atual.")
    private Integer anoIngresso;

    private boolean status;

    public boolean getStatus() {
        return status;
    }
}
