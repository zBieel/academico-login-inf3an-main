package com.itb.lip2.academicologininf3an.controller;


import com.itb.lip2.academicologininf3an.model.Aluno;
import com.itb.lip2.academicologininf3an.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/academico/api/v1/alunos")
public class AlunoCrontroller {

    private final AlunoService alunoService;

    AlunoCrontroller (AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object>updateAluno(@RequestBody Aluno aluno, @PathVariable(value="id") Long id) {
        try {
            return ResponseEntity.ok().body(alunoService.update(id, aluno));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

}
