package com.itb.lip2.academicologininf3an.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.itb.lip2.academicologininf3an.model.Papel;
import com.itb.lip2.academicologininf3an.repository.PapelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.itb.lip2.academicologininf3an.model.Usuario;
import com.itb.lip2.academicologininf3an.repository.UsuarioRepository;

import javax.transaction.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private PapelRepository papelRepository;

	@Override
	public Usuario save(Usuario usuario) {
		usuario.setCodStatusUsuario(true);
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		return usuarioRepository.save(usuario);
	}

	@Override
	public List<Usuario> findAll() {
		
		return usuarioRepository.findAll();
	}

	@Override
	public Optional<Usuario> findById(Long id) {

		Optional <Usuario> usuario =  usuarioRepository.findById(id);
		return usuario;
	}

	@Override
	@Transactional
	public Usuario update(Long id, Usuario usuario) throws Exception {
		return usuarioRepository.findById(id).map(user ->{
			user.setNome(usuario.getNome());
			user.setSobrenome(usuario.getSobrenome());
			user.setDataNascimento(usuario.getDataNascimento());
			return usuarioRepository.save(user);
        }).orElseThrow(()-> new Exception("Usuário não encontrado!"));
	}

	@Override
	@Transactional
	public Usuario delete(Long id) throws Exception {
		if (!usuarioRepository.existsById(id)) {
			throw new Exception("Usuário não encontrado com ID: " + id);
		}
		usuarioRepository.deleteById(id);
		return null;
	}

	@Override
	public Papel savePapel(Papel papel) {
		return papelRepository.save(papel);
	}

	@Override
	public void addPapelToUsuario(Usuario usuario, String nomePapel) {
		Papel papel = papelRepository.findByName(nomePapel);
		usuario.getPapeis().add(papel);
	}

	@Override
	public Usuario findByUsername(String username) {
		return usuarioRepository.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


		Usuario usuario = usuarioRepository.findByUsername(username);
		if(usuario == null) {
			throw  new UsernameNotFoundException("Usuário não encontrado no banco de dados");
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		usuario.getPapeis().forEach(papel -> {
			authorities.add(new SimpleGrantedAuthority(papel.getNomePapel()));
		});

		return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getSenha(), authorities);
	}

		// New method for authentication
	public Usuario authenticate(String username, String password) throws Exception {
		Usuario usuario = findByUsername(username);
		if (usuario == null) {
			throw new Exception("Usuário não encontrado!");
		}
		if (!passwordEncoder.matches(password, usuario.getSenha())) {
			throw new Exception("Senha incorreta!");
		}
		return usuario;
	}
}
