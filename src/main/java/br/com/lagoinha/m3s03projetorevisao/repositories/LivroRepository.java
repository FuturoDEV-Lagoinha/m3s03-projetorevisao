package br.com.lagoinha.m3s03projetorevisao.repositories;

import br.com.lagoinha.m3s03projetorevisao.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
}
