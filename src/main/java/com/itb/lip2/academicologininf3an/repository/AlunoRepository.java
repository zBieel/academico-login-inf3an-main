package com.itb.lip2.academicologininf3an.repository;

import com.itb.lip2.academicologininf3an.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long >{
}
