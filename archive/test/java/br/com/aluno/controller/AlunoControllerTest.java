package br.com.aluno.controller;

import br.com.aluno.config.GlobalExceptionHandler;
import br.com.aluno.dto.AlunoDTORequest;
import br.com.aluno.dto.AlunoDTOResponse;
import br.com.aluno.entity.Alunos;
import br.com.aluno.service.AlunoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;
import java.util.List;

import static br.com.aluno.service.factory.AlunoFactory.criarAlunoDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
public class AlunoControllerTest {

    private MockMvc mvc;

    @Mock
    private AlunoService alunoService;

    @InjectMocks
    private AlunoController alunoController;

    private JacksonTester<Alunos> jsonAlunos;
    private JacksonTester<AlunoDTORequest> jsonAlunoDTORequest;
    private JacksonTester<AlunoDTOResponse> jsonAlunoDTOResponse;

    AlunoDTOResponse alunoDTOResponse;
    AlunoDTORequest alunoDTORequest;

    @BeforeEach
    public void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        JacksonTester.initFields(this, objectMapper);
        mvc = MockMvcBuilders.standaloneSetup(alunoController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        alunoDTOResponse = AlunoDTOResponse.builder()
                .nomeAluno("Maria Oliveira")
                .cpf("98765432100")
                .dataNascimento(LocalDate.of(2001, 8, 20))
                .emailAluno("maria.oliveira@email.com")
                .telefoneAluno("11955554444")
                .curso("Engenharia de Software")
                .turma("Turma C")
                .dataCadastro(LocalDate.now())
                .build();
    }

    @Test
    void deveInserirAluno() throws Exception {
        AlunoDTORequest request = criarAlunoDTO();
        doNothing().when(alunoService).inserirAluno(any(AlunoDTORequest.class));

        MockHttpServletResponse resultado = mvc.perform(
                        post("/v1/alunos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonAlunoDTORequest.write(request).getJson()))
                .andReturn()
                .getResponse();

        assertEquals(CREATED.value(), resultado.getStatus());
    }

    @Test
    void deveBuscarAluno() throws Exception {
        when(alunoService.buscarAlunos()).thenReturn(List.of(alunoDTOResponse));

        MockHttpServletResponse resultado = mvc.perform(
                        get("/v1/alunos")
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(OK.value(), resultado.getStatus());
    }

    @Test
    void deveBuscarAlunoPorMatricula() throws Exception {
        when(alunoService.buscarAlunoPorMatricula(1L)).thenReturn(alunoDTOResponse);

        MockHttpServletResponse resultado = mvc.perform(
                        get("/v1/alunos/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(OK.value(), resultado.getStatus());
    }

    @Test
    void deveAtualizarAluno() throws Exception {
        alunoDTORequest = criarAlunoDTO();
        when(alunoService.atualizarAluno(eq(1L), any(AlunoDTORequest.class))).thenReturn(alunoDTOResponse);

        MockHttpServletResponse resultado = mvc.perform(
                        patch("/v1/alunos/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonAlunoDTORequest.write(alunoDTORequest).getJson()))
                .andReturn()
                .getResponse();

        assertEquals(OK.value(), resultado.getStatus());
    }

    @Test
    void deveDeletarAluno() throws Exception {
        doNothing().when(alunoService).deletarAluno(1L);

        MockHttpServletResponse resultado = mvc.perform(
                        delete("/v1/alunos/1"))
                .andReturn()
                .getResponse();

        assertEquals(NO_CONTENT.value(), resultado.getStatus());
    }


}
