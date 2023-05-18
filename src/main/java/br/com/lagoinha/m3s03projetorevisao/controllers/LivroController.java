package br.com.lagoinha.m3s03projetorevisao.controllers;

import br.com.lagoinha.m3s03projetorevisao.entities.Livro;
import br.com.lagoinha.m3s03projetorevisao.services.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("livro")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping
    public List<Livro> get() {
        return livroService.buscarTodos();
    }

    @GetMapping("{id}")
    public ResponseEntity getId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(livroService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity post(@RequestBody Livro livro) {
        try {
            livro.setId(null);
            return ResponseEntity.ok(livroService.salvar(livro));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable Long id, @RequestBody Livro livro) {
        try {
            livro.setId(id);
            return ResponseEntity.ok(livroService.salvar(livro));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(livroService.apagar(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
