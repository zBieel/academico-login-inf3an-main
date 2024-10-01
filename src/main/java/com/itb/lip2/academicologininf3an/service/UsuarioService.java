package com.itb.lip2.academicologininf3an.service;

import com.itb.lip2.academicologininf3an.model.Papel;
import com.itb.lip2.academicologininf3an.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
	Usuario save(Usuario usuario);
	List<Usuario> findAll();
	Optional<Usuario> findById(Long id);
	Usuario update(Long id, Usuario usuario) throws Exception;
	void delete(Long id) throws Exception;
	Papel savePapel(Papel papel);  // Método para salvar papel
	void addPapelToUsuario(Usuario usuario, String nomePapel);
	Usuario authenticate(String nome, String senha) throws Exception;
	Usuario findByUsername(String username);
	PasswordEncoder getPasswordEncoder();
}
