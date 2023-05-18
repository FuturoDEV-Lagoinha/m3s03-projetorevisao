package br.com.lagoinha.m3s03projetorevisao.repositories;

import br.com.lagoinha.m3s03projetorevisao.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
