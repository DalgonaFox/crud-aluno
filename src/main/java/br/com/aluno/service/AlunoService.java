package br.com.aluno.service;

import br.com.aluno.config.CursoEnum;
import br.com.aluno.dto.AlunoDTORequest;
import br.com.aluno.dto.AlunoDTOResponse;
import br.com.aluno.entity.Alunos;
import br.com.aluno.exception.AlunoException;
import br.com.aluno.mapper.AlunoMapper;
import br.com.aluno.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.aluno.config.Constants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final AlunoMapper alunoMapper;

    public void inserirAluno(AlunoDTORequest alunoDTORequest) {
        Alunos aluno = alunoMapper.alunoDTOParaAlunos(alunoDTORequest);

        if (alunoRepository.findByMatricula(aluno.getMatricula()) != null) {
            throw new AlunoException(MENSAGEM_MATRICULA_DUPLICADA, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        try {
            alunoRepository.save(aluno);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AlunoException(MENSAGEM_GENERICA, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<AlunoDTOResponse> buscarAlunos() {
        return alunoRepository.findAll().stream().map(alunoMapper::alunosParaAlunoDTO).collect(Collectors.toList());
    }

    public AlunoDTOResponse buscarAlunoPorMatricula(Long matricula) {
        Alunos aluno = alunoRepository.findById(matricula).orElseThrow(() -> new AlunoException(MENSAGEM_ALUNO_NAO_ENCONTRADO, HttpStatus.NOT_FOUND));
        return new AlunoDTOResponse(
                aluno.getMatricula(),
                aluno.getNomeAluno(),
                aluno.getEmailAluno(),
                aluno.getCurso().toString(),
                aluno.getAnoIngresso(),
                aluno.getStatus() ? "Ativo" : "Trancado"
        );
    }

    public void deletarAluno(Long matricula) {
        alunoRepository.deleteById(matricula);
    }

    public AlunoDTOResponse atualizarAluno(Long matricula, AlunoDTORequest alunoDTORequest) {
        Optional<Alunos> aluno = alunoRepository.findById(matricula);
        if (aluno.isPresent()) {
            var a = aluno.get();
            if (alunoDTORequest.getNomeAluno() != null) a.setNomeAluno(alunoDTORequest.getNomeAluno());
            if (alunoDTORequest.getEmailAluno() != null) a.setEmailAluno(alunoDTORequest.getEmailAluno());
            if (alunoDTORequest.getCurso() != null) a.setCurso(alunoDTORequest.getCurso());

            return alunoMapper.alunosParaAlunoDTO(alunoRepository.save(a));
        } else {
            throw new AlunoException(MENSAGEM_ALUNO_NAO_ENCONTRADO, HttpStatus.NOT_FOUND);
        }
    }

    public AlunoDTOResponse trancarMatricula(Long matricula) {
        Optional<Alunos> aluno = alunoRepository.findById(matricula);
        if (aluno.isPresent()) {
            var a = aluno.get();
            if(a.getStatus()) a.setStatus(false);
            else throw new AlunoException(MENSAGEM_MATRICULA_TRANCADA, HttpStatus.CONFLICT);
            return alunoMapper.alunosParaAlunoDTO(alunoRepository.save(a));
        } else {
            throw new AlunoException(MENSAGEM_ALUNO_NAO_ENCONTRADO, HttpStatus.NOT_FOUND);
        }
    }

    public AlunoDTOResponse reabrirMatricula(Long matricula) {
        Optional<Alunos> aluno = alunoRepository.findById(matricula);
        if (aluno.isPresent()) {
            var a = aluno.get();
            if(!a.getStatus()) a.setStatus(true);
            else throw new AlunoException(MENSAGEM_MATRICULA_ABERTA, HttpStatus.CONFLICT);
            return alunoMapper.alunosParaAlunoDTO(alunoRepository.save(a));
        } else {
            throw new AlunoException(MENSAGEM_ALUNO_NAO_ENCONTRADO, HttpStatus.NOT_FOUND);
        }
    }

    public List<AlunoDTOResponse> buscarAlunoPorCurso(CursoEnum curso) {
        return alunoRepository.findByCurso(curso).stream().map(alunoMapper::alunosParaAlunoDTO).collect(Collectors.toList());
    }
}
