package br.com.lagoinha.m3s03projetorevisao.services;

import br.com.lagoinha.m3s03projetorevisao.entities.Autor;
import br.com.lagoinha.m3s03projetorevisao.repositories.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    // Salvar / Consultar / Apagar

    public List<Autor> buscarTodos() {
        return autorRepository.findAll();
    }

    public Autor salvar(Autor autor) throws Exception {

        // Se está editando e "AUTOR" não existir
        if (autor.getId() != null) {
            buscarPorId(autor.getId());
        }

        if (autor.getNome() == null || autor.getNome().isEmpty()) {
            throw new Exception("Nome é obrigatório!");
        }

        if (autor.getSobrenome() == null || autor.getSobrenome().isEmpty()) {
            throw new Exception("Sobrenome é obrigatório!");
        }

        autor = autorRepository.save(autor);

        return autor;
    }

    public Autor buscarPorId(Long id) throws Exception {
        Optional<Autor> autorOpt = autorRepository.findById(id);
        if (autorOpt.isEmpty()) {
            throw new Exception("Autor não encontrado!");
        }
        return autorOpt.get();
    }

    public boolean apagar(Long id) throws Exception {
        Autor autor = buscarPorId(id);
        try {
            autorRepository.delete(autor);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
