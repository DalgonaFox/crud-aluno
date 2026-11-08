package br.com.aluno.repository;

import br.com.aluno.config.CursoEnum;
import br.com.aluno.entity.Alunos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlunoRepository extends
        JpaRepository<Alunos, Long> {
            Alunos findByMatricula(Long matricula);
            List<Alunos> findByCurso(CursoEnum curso);
        }
