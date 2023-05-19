package br.com.lagoinha.m3s03projetorevisao.controllers;

import br.com.lagoinha.m3s03projetorevisao.controllers.objects.EmprestimoDevolucaoReq;
import br.com.lagoinha.m3s03projetorevisao.entities.Emprestimo;
import br.com.lagoinha.m3s03projetorevisao.services.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprestimo")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @GetMapping
    public List<Emprestimo> get() {
        return emprestimoService.buscarTodos();
    }

    @GetMapping("{id}")
    public ResponseEntity getId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(emprestimoService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity post(@RequestBody Emprestimo emprestimo) {
        try {
            Emprestimo empresimoCriado = emprestimoService.criar(emprestimo);
            return ResponseEntity.ok(empresimoCriado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("devolucao")
    public ResponseEntity putDevolucao(@RequestBody EmprestimoDevolucaoReq body) {
        try {
            return ResponseEntity.ok(emprestimoService.devolucao(body.getUsuarioId(), body.getLivroId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
