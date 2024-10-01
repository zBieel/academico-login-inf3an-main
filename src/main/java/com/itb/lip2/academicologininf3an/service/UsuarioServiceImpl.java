package com.itb.lip2.academicologininf3an.service;

import com.itb.lip2.academicologininf3an.model.Papel;
import com.itb.lip2.academicologininf3an.model.Usuario;
import com.itb.lip2.academicologininf3an.repository.PapelRepository;
import com.itb.lip2.academicologininf3an.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PapelRepository papelRepository;  // Certifique-se de que está injetado corretamente

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Usuario save(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@Override
	public Usuario authenticate(String email, String senha) {
		Usuario usuario = findByUsername(email);
		if (usuario != null && passwordEncoder.matches(senha, usuario.getSenha())) {
			return usuario;
		}
		throw new RuntimeException("Usuário ou senha inválidos");
	}

	@Override
	public Usuario findByUsername(String email) {
		return usuarioRepository.findByEmail(email);
	}

	@Override
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	@Override
	public Optional<Usuario> findById(Long id) {
		return usuarioRepository.findById(id);
	}

	@Override
	public void delete(Long id) {
		usuarioRepository.deleteById(id);
	}

	@Override
	public Usuario update(Long id, Usuario usuario) {
		// Lógica de atualização
		return usuarioRepository.save(usuario);
	}

	@Override
	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	@Override
	public Papel savePapel(Papel papel) {
		return papelRepository.save(papel); // Salva o papel no repositório
	}

	@Override
	@Transactional
	public void addPapelToUsuario(Usuario usuario, String nomePapel) {
		// Encontrar o papel pelo nome
		Papel papel = papelRepository.findByName(nomePapel);
		if (papel == null) {
			throw new RuntimeException("Papel não encontrado: " + nomePapel);
		}
		// Adicionar o papel ao usuário
		usuario.getPapeis().add(papel);
		// Salvar o usuário com o novo papel
		usuarioRepository.save(usuario);
	}

	// Implementação de UserDetailsService
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(username);
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário não encontrado: " + username);
		}
		// Converter Usuario para UserDetails (classe padrão do Spring Security)
		return new org.springframework.security.core.userdetails.User(
				usuario.getEmail(),
				usuario.getSenha(),
				usuario.getPapeis().stream().map(papel -> new SimpleGrantedAuthority(papel.getNomePapel())).collect(Collectors.toList())
		);
	}
}
