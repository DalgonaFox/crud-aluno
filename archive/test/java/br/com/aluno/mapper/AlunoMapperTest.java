package br.com.aluno.mapper;

import br.com.aluno.dto.AlunoDTORequest;
import br.com.aluno.dto.AlunoDTOResponse;
import br.com.aluno.entity.Alunos;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static br.com.aluno.service.factory.AlunoFactory.criarAlunoDTO;
import static br.com.aluno.service.factory.AlunoFactory.criarAlunoEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class AlunoMapperTest {

    @InjectMocks
    private AlunoMapper alunoMapper = Mappers.getMapper(AlunoMapper.class);


    AlunoDTORequest alunoDTO;
    Alunos alunoEntity;

    @Test
    void deveConverterEntityParaDTO() {
        alunoEntity = criarAlunoEntity();
        AlunoDTOResponse alunoDTOResponse = alunoMapper.alunosParaAlunoDTO(alunoEntity);

        assertEquals("João Silva", alunoDTOResponse.getNomeAluno());
        assertEquals("12345678901", alunoDTOResponse.getCpf());
        assertEquals(LocalDate.parse("2000-05-15"), alunoDTOResponse.getDataNascimento());
        assertEquals("joao.silva@email.com", alunoDTOResponse.getEmailAluno());
        assertEquals("11987654321", alunoDTOResponse.getTelefoneAluno());
        assertEquals("Engenharia de Software", alunoDTOResponse.getCurso());
        assertEquals("Turma A", alunoDTOResponse.getTurma());
    }

    @Test
    void deveConverterDTOParaEntity() {
        alunoDTO = criarAlunoDTO();
        Alunos aluno = alunoMapper.alunoDTOParaAlunos(alunoDTO);

        assertEquals("João Silva", aluno.getNomeAluno());
        assertEquals("12345678901", aluno.getCpf());
        assertEquals(LocalDate.parse("2000-05-15"), aluno.getDataNascimento());
        assertEquals("joao.silva@email.com", aluno.getEmailAluno());
        assertEquals("11987654321", aluno.getTelefoneAluno());
        assertEquals("Engenharia de Software", aluno.getCurso());
        assertEquals("Turma A", aluno.getTurma());
    }

    @Test
    void deveGerarDataCriacao() {
        AlunoDTORequest aluno = criarAlunoDTO();

        alunoMapper.gerarInformacoesCadastro(aluno);

        assertEquals(LocalDate.now(), aluno.getDataCadastro());
    }

    @Test
    void deveNaoGerarDataCriacao() {
        AlunoDTORequest aluno = null;
        alunoMapper.gerarDataCriacao(aluno);
        assertNull(aluno);
    }

}
