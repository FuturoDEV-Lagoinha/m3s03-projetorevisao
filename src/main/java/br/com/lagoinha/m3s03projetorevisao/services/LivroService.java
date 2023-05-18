package br.com.lagoinha.m3s03projetorevisao.services;

import br.com.lagoinha.m3s03projetorevisao.entities.Autor;
import br.com.lagoinha.m3s03projetorevisao.entities.Livro;
import br.com.lagoinha.m3s03projetorevisao.repositories.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorService autorService;

    // Salvar / Consultar / Apagar

    public List<Livro> buscarTodos() {
        return livroRepository.findAll();
    }

    public Livro salvar(Livro livro) throws Exception {

        // Se está editando e "Livro" não existir
        if (livro.getId() != null) {
            buscarPorId(livro.getId());
        }

        if (livro.getTitulo() == null || livro.getTitulo().isEmpty()) {
            throw new Exception("Título é obrigatório!");
        }

        if (livro.getAutor() == null) {
            throw new Exception("Autor é obrigatório!");
        }

        // Exemplo de cadastro de "Autor" junto ao "Livro"
        Autor autor;
        try {
            autor = autorService.buscarPorId(livro.getAutor().getId());
        } catch (Exception e) {
            autor = autorService.salvar(livro.getAutor());
        }
        livro.setAutor(autor);

        if (livro.getCategoria() == null || livro.getCategoria().isEmpty()) {
            throw new Exception("Categoria é obrigatório!");
        }

        if (livro.getEditora() == null || livro.getEditora().isEmpty()) {
            throw new Exception("Editora é obrigatório!");
        }

        if (livro.getExemplares() == null || livro.getExemplares() < 0) {
            livro.setExemplares(0);
        }

        livro.setDisponibilidade(livro.getExemplares() > 0);

        livro = livroRepository.save(livro);

        return livro;
    }

    public Livro buscarPorId(Long id) throws Exception {
        Optional<Livro> livroOpt = livroRepository.findById(id);
        if (livroOpt.isEmpty()) {
            throw new Exception("Livro não encontrado!");
        }
        return livroOpt.get();
    }

    public boolean apagar(Long id) throws Exception {
        Livro livro = buscarPorId(id);
        try {
            livroRepository.delete(livro);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
