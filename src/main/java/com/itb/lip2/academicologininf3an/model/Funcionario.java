package com.itb.lip2.academicologininf3an.model;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Collection;

@Entity
@DiscriminatorValue(value = "Funcionario")
public class Funcionario extends Usuario {

    public Funcionario() {


    }
    public Funcionario(Long id, String nome, String sobrenome,String email, String senha, String tipoUsuario, Collection<Papel> papeis) {
        super(id, nome, sobrenome, email, senha,tipoUsuario, papeis);  // Acesso ao construtor da classe pai
    }

}
