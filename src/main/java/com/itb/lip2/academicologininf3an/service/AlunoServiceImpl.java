package com.itb.lip2.academicologininf3an.service;


import com.itb.lip2.academicologininf3an.model.Aluno;
import com.itb.lip2.academicologininf3an.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AlunoServiceImpl implements AlunoService {

    // @Autowired
   // private  AlunoRepository alunoRepository;
    private final AlunoRepository alunoRepository;

    public AlunoServiceImpl(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;

    }
    @Override
    @Transactional
    public Aluno update(Long id, Aluno aluno) throws Exception {
        return alunoRepository.findById(id).map(al ->{
            al.setNome(aluno.getNome());
            al.setSobrenome(aluno.getSobrenome());
            al.setEmail(aluno.getEmail());
            al.setDataNascimento(aluno.getDataNascimento());
            al.setRm(aluno.getRm());
            al.setCodStatusUsuario(aluno.isCodStatusUsuario());
            return alunoRepository.save(al);
        }).orElseThrow(()->new Exception("Aluno não encontrado!"));
    }
}
