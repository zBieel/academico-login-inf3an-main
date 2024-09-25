package com.itb.lip2.academicologininf3an.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;
import java.util.Collection;
import javax.persistence.*;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipoUsuario", discriminatorType = DiscriminatorType.STRING)
@EnableJpaAuditing
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipoUsuario")
@JsonSubTypes({
@JsonSubTypes.Type(value = Aluno.class, name = "Aluno"),
@JsonSubTypes.Type(value = Funcionario.class, name = "Funcionario")
})
public abstract class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-Incremento
    protected Long id;
    protected String nome;
    protected String sobrenome;
    protected String email;
    protected String senha; // Este campo deve ser tratado com segurança
    protected boolean codStatusUsuario;
    protected LocalDate dataNascimento;

    @Column(insertable = false, updatable = false)
    protected String tipoUsuario;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // M: N
    @JoinTable(
        name="usuarios_papeis",
        joinColumns = @JoinColumn(name="usuario_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name="papel_id", referencedColumnName = "id")
    )
    private Collection<Papel> papeis;

    public Usuario() {
    }

    public Usuario(Long id, String nome, String sobrenome, String email, String senha, String tipoUsuario, Collection<Papel> papeis) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
        this.papeis = papeis;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isCodStatusUsuario() {
        return codStatusUsuario;
    }

    public void setCodStatusUsuario(boolean codStatusUsuario) {
        this.codStatusUsuario = codStatusUsuario;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Collection<Papel> getPapeis() {
        return papeis;
    }

    public void setPapeis(Collection<Papel> papeis) {
        this.papeis = papeis;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
