package br.com.aluno.service;

import br.com.aluno.dto.AlunoDTORequest;
import br.com.aluno.dto.AlunoDTOResponse;
import br.com.aluno.entity.Alunos;
import br.com.aluno.exception.AlunoException;
import br.com.aluno.mapper.AlunoMapper;
import br.com.aluno.repository.AlunoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static br.com.aluno.config.Constants.MENSAGEM_GENERICA;
import static br.com.aluno.config.Constants.MENSAGEM_MATRICULA_DUPLICADA;
import static br.com.aluno.service.factory.AlunoFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ExtendWith(MockitoExtension.class)
public class AlunoServiceTest {

    @InjectMocks
    private AlunoService alunoService;

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private AlunoMapper alunoMapper;

    void inserirAluno(Alunos alunoEntity) {
        AlunoDTORequest alunoDTO = criarAlunoDTO();

        doReturn(alunoEntity).when(alunoMapper).alunoDTOParaAlunos(alunoDTO);

        alunoService.inserirAluno(alunoDTO);
    }

    @Test
    void deveCriarAluno() {
        inserirAluno(criarAlunoEntity());

        verify(alunoRepository, times(1))
                .save(any(Alunos.class));
    }

    @Test
    void deveCriarAlunoRetornarExcecaoGenerica() {
        Alunos alunoEntity = criarAlunoEntity();
        AlunoDTORequest alunoDTO = criarAlunoDTO();

        doReturn(alunoEntity).when(alunoMapper).alunoDTOParaAlunos(alunoDTO);
        when(alunoRepository.save(any())).thenThrow(AlunoException.class);

        AlunoException e = assertThrows(AlunoException.class, () -> {
            alunoService.inserirAluno(alunoDTO);
        });

        assertEquals(MENSAGEM_GENERICA, e.getMessage());
        assertEquals(INTERNAL_SERVER_ERROR, e.getCodigoErro());
    }

    @Test
    void deveCriarAlunoRetornarExcecaoMatriculaDuplicada() {
        Alunos alunoEntity = criarAlunoEntity();
        AlunoDTORequest alunoDTO = criarAlunoDTO();

        doReturn(alunoEntity).when(alunoMapper).alunoDTOParaAlunos(alunoDTO);
        when(alunoRepository.findByMatricula(any())).thenReturn(alunoEntity);

        AlunoException e = assertThrows(AlunoException.class, () -> {
            alunoService.inserirAluno(alunoDTO);
        });

        assertEquals(MENSAGEM_MATRICULA_DUPLICADA, e.getMessage());
        assertEquals(UNPROCESSABLE_ENTITY, e.getCodigoErro());
    }

    @Test
    void deveBuscarAlunos() {
        Alunos alunos = criarAlunoEntity();

        when(alunoRepository.findAll(any(Example.class))).thenReturn(List.of(alunos));

        final var listaDeAlunos = alunoService.buscarAlunos();

        assertEquals(1, listaDeAlunos.size());
        verify(alunoRepository, times(1)).findAll(any(Example.class));
    }

    @Test
    void deveBuscarAlunoPorMatricula() {
        Alunos aluno = criarAlunoEntity();
        final Long id = 1L;

        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));

        final AlunoDTOResponse alunoDTO = alunoService.buscarAlunoPorMatricula(id);

        verify(alunoRepository, times(1)).findById(id);

        assertEquals("João Silva", alunoDTO.getNomeAluno());
        assertEquals("12345678901", alunoDTO.getCpf());
        assertEquals(LocalDate.parse("2000-05-15"), alunoDTO.getDataNascimento());
        assertEquals("joao.silva@email.com", alunoDTO.getEmailAluno());
        assertEquals("11987654321", alunoDTO.getTelefoneAluno());
        assertEquals("Engenharia de Software", alunoDTO.getCurso());
        assertEquals("Turma A", alunoDTO.getTurma());

    }

    @Test
    void deveAtualizarAluno() {
        inserirAluno(criarAlunoEntity());
        final var aluno = atualizarAlunoDTO();
        final Long id = 1L;

        Alunos alunoExiste = criarAlunoEntity();

        when(alunoRepository.findById(id)).thenReturn(Optional.of(alunoExiste));

        alunoService.atualizarAluno(id, aluno);

        verify(alunoRepository, times(1)).findById(id);
        verify(alunoRepository, times(2)).save(any(Alunos.class));

        assertEquals("João Silva Atualizado", alunoExiste.getNomeAluno());
        assertEquals("10987654321", alunoExiste.getCpf());
        assertEquals(LocalDate.parse("2000-05-15"), alunoExiste.getDataNascimento());
        assertEquals("joao.atualizado@email.com", alunoExiste.getEmailAluno());
        assertEquals("11912345678", alunoExiste.getTelefoneAluno());
        assertEquals("Ciência da Computação", alunoExiste.getCurso());
        assertEquals("Turma B", alunoExiste.getTurma());

    }

    @Test
    void deveFalharAoAtualizarAluno() {
        final var aluno = atualizarAlunoDTO();
        final Long id = 99L;

        when(alunoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> alunoService.atualizarAluno(id, aluno));

        verify(alunoRepository, times(1)).findById(id);
        verify(alunoRepository, times(0)).save(any(Alunos.class));

    }

    @Test
    void deveNaoAtualizarAlunoPorNull() {
        final var aluno = criarAlunoEntity();
        final Long id = 99L;

        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));

        final AlunoDTORequest alunoNulo = criarAlunoNuloDTO();

        alunoService.atualizarAluno(id, alunoNulo);

        verify(alunoRepository, times(1)).findById(id);
        verify(alunoRepository, times(1)).save(any(Alunos.class));

        assertEquals("João Silva", aluno.getNomeAluno());
        assertEquals("12345678901", aluno.getCpf());
        assertEquals(LocalDate.parse("2000-05-15"), aluno.getDataNascimento());
        assertEquals("joao.silva@email.com", aluno.getEmailAluno());
        assertEquals("11987654321", aluno.getTelefoneAluno());
        assertEquals("Engenharia de Software", aluno.getCurso());
        assertEquals("Turma A", aluno.getTurma());

    }

    @Test
    void deveApagarAluno() {
        inserirAluno(criarAlunoEntity());
        final Long id = 1L;

        alunoService.deletarAluno(id);

        verify(alunoRepository, times(1)).deleteById(id);
    }

}
