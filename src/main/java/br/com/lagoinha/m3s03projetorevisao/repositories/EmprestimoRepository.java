package br.com.lagoinha.m3s03projetorevisao.repositories;

import br.com.lagoinha.m3s03projetorevisao.entities.Emprestimo;
import br.com.lagoinha.m3s03projetorevisao.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    // DerivedQuery
    List<Emprestimo> findByUsuarioOrderByDataEmprestimoDesc(Usuario usuario);

    // JPQL
    @Query( "SELECT el.emprestimo " +
            "FROM EmprestimoLivro el " +
            "WHERE el.livro.id = :livroId " +
            "  AND el.emprestimo.usuario.id = :usuarioId " +
            "  AND el.dataDevolucao IS NULL " +
            "ORDER BY el.dataPrevista")
    List<Emprestimo> findByUsuarioIdAndLivroIdDisponiveis(Long usuarioId, Long livroId);

    @Query( "SELECT el.emprestimo " +
            "FROM EmprestimoLivro el " +
            "WHERE el.emprestimo.usuario.id = :usuarioId " +
            "  AND el.dataDevolucao IS NULL " +
            "ORDER BY el.dataPrevista")
    List<Emprestimo> findAllPendentes(Long usuarioId);

}
