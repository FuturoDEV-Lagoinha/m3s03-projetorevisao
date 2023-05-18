package br.com.lagoinha.m3s03projetorevisao.controllers;

import br.com.lagoinha.m3s03projetorevisao.entities.Usuario;
import br.com.lagoinha.m3s03projetorevisao.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> get() {
        return usuarioService.buscarTodos();
    }

    @GetMapping("{id}")
    public ResponseEntity getId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity post(@RequestBody Usuario usuario) {
        try {
            usuario.setId(null);
            return ResponseEntity.ok(usuarioService.salvar(usuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            usuario.setId(id);
            return ResponseEntity.ok(usuarioService.salvar(usuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioService.apagar(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
