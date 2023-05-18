package br.com.lagoinha.m3s03projetorevisao.controllers;

import br.com.lagoinha.m3s03projetorevisao.entities.Autor;
import br.com.lagoinha.m3s03projetorevisao.services.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("autor")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping
    public List<Autor> get() {
        return autorService.buscarTodos();
    }

    @GetMapping("{id}")
    public ResponseEntity getId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(autorService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity post(@RequestBody Autor autor) {
        try {
            autor.setId(null);
            return ResponseEntity.ok(autorService.salvar(autor));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity put(@PathVariable Long id, @RequestBody Autor autor) {
        try {
            autor.setId(id);
            return ResponseEntity.ok(autorService.salvar(autor));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(autorService.apagar(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
