package br.com.aluno.repository;

import br.com.aluno.entity.Alunos;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static br.com.aluno.service.factory.AlunoFactory.criarAlunoEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    private Alunos aluno;

    @BeforeEach
    void setUp() {
        aluno = criarAlunoEntity();
        alunoRepository.save(aluno);
    }

    @AfterEach
    void tearDown() {
        alunoRepository.delete(aluno);
    }

    @Test
    void deveEncontrarAlunoPorRacf() {
        aluno.setMatricula(1L);
        alunoRepository.save(aluno);

        Alunos alunoAtualizado = alunoRepository.findByMatricula(aluno.getMatricula());

        assertNotNull(alunoAtualizado);
        assertEquals(1L, alunoAtualizado.getMatricula());
    }
}
