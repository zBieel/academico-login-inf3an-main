package com.itb.lip2.academicologininf3an.service;

import java.util.List;
import java.util.Optional;

import com.itb.lip2.academicologininf3an.model.Papel;
import com.itb.lip2.academicologininf3an.model.Usuario;

public interface UsuarioService {
	
	Usuario save(Usuario usuario);
	List<Usuario> findAll();
	Optional<Usuario> findById(Long id);
	Usuario update(Long id, Usuario usuario) throws Exception;
	Usuario delete(Long id) throws Exception;
	Papel savePapel(Papel papel);
	void addPapelToUsuario(Usuario usuario, String nomePapel);
	Usuario authenticate(String nome, String senha) throws Exception;
	Usuario findByUsername(String username);

}
