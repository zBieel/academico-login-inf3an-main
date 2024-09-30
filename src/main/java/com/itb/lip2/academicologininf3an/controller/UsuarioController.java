package com.itb.lip2.academicologininf3an.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.itb.lip2.academicologininf3an.model.Usuario;
import com.itb.lip2.academicologininf3an.service.UsuarioService;

@RestController
@RequestMapping("/academico/api/v1")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/users")
    public ResponseEntity<List<Usuario>> getUsers() {
        return ResponseEntity.ok().body(usuarioService.findAll());
    }

    @PostMapping("/users")
    public ResponseEntity<Usuario> saveUser(@RequestBody Usuario usuario) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/academico/api/v1/users").toUriString());
        return ResponseEntity.created(uri).body(usuarioService.save(usuario));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> findUserById(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok().body(usuarioService.findById(id).get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") Long id, @RequestBody Usuario usuario) {
        try {
            return ResponseEntity.ok().body(usuarioService.update(id, usuario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") Long id) {
        try {
            usuarioService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Usuario loginRequest) {
        try {
            Usuario user = usuarioService.authenticate(loginRequest.getNome(), loginRequest.getSenha());
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    
}