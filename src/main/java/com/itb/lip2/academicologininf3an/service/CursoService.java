package com.itb.lip2.academicologininf3an.service;

import com.itb.lip2.academicologininf3an.model.Curso;
import com.itb.lip2.academicologininf3an.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface CursoService {
 List<Curso> findAll();


}
