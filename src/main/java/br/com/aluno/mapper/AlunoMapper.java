package br.com.aluno.mapper;

import br.com.aluno.dto.AlunoDTORequest;
import br.com.aluno.dto.AlunoDTOResponse;
import br.com.aluno.entity.Alunos;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.Year;

@Mapper(componentModel = "spring")
public interface AlunoMapper {

    Alunos alunoDTOParaAlunos(AlunoDTORequest alunoDTORequest);

    @Mapping(target = "status", expression = "java(aluno.getStatus() ? \"Ativo\" : \"Trancado\")")
    AlunoDTOResponse alunosParaAlunoDTO(Alunos aluno);

    @BeforeMapping
    default void gerarInformacoesCadastro(AlunoDTORequest alunoDTORequest) {
        if (alunoDTORequest != null) {
            alunoDTORequest.setAnoIngresso(Year.now().getValue());
            alunoDTORequest.setStatus(true);
        }
    }
}
