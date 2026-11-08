package br.com.aluno.controller;

import br.com.aluno.config.CursoEnum;
import br.com.aluno.dto.AlunoDTORequest;
import br.com.aluno.dto.AlunoDTOResponse;
import br.com.aluno.service.AlunoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/v1/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;

    @PostMapping
    private ResponseEntity<Void> inserirAluno(@Valid @NotNull @RequestBody AlunoDTORequest alunoDTO) {
        alunoService.inserirAluno(alunoDTO);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping
    private ResponseEntity<List<AlunoDTOResponse>> buscarAlunos() {
        var alunos = alunoService.buscarAlunos();
        return ResponseEntity.ok().body(alunos);
    }

    @GetMapping("/{matricula}")
    private ResponseEntity<AlunoDTOResponse> buscarAlunosPorMatricula(@PathVariable("matricula") Long matricula) {
        var aluno = alunoService.buscarAlunoPorMatricula(matricula);
        return ResponseEntity.ok().body(aluno);
    }

    @PatchMapping("/{matricula}")
    private @NotNull ResponseEntity<AlunoDTOResponse> atualizarAluno(@Valid @PathVariable("matricula") Long matricula, @RequestBody AlunoDTORequest alunoDTORequest) {
        var aluno = alunoService.atualizarAluno(matricula, alunoDTORequest);
        return ResponseEntity.ok().body(aluno);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deletarAluno(@PathVariable("id") Long id) {
        alunoService.deletarAluno(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @PatchMapping("/trancar/{matricula}")
    private ResponseEntity<AlunoDTOResponse> trancarMatricula(@PathVariable("matricula") Long matricula) {
        var aluno = alunoService.trancarMatricula(matricula);
        return ResponseEntity.ok().body(aluno);
    }

    @PatchMapping("/reabrir/{matricula}")
    private ResponseEntity<AlunoDTOResponse> reabrirMatricula(@PathVariable("matricula") Long matricula) {
        var aluno = alunoService.reabrirMatricula(matricula);
        return ResponseEntity.ok().body(aluno);
    }

    @GetMapping("/buscarPorCurso/{curso}")
    private ResponseEntity<List<AlunoDTOResponse>> buscarAlunosPorCurso(@PathVariable("curso") CursoEnum curso) {
        var alunos = alunoService.buscarAlunoPorCurso(curso);
        return ResponseEntity.ok().body(alunos);
    }

}
