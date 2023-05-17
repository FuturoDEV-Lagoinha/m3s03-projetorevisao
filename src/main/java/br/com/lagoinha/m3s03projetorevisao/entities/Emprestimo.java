package br.com.lagoinha.m3s03projetorevisao.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Emprestimo {

    @Id
    @Column(name = "emprestimo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private Date dataEmprestimo = new Date();

    @OneToMany(mappedBy = "emprestimo", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<EmprestimoLivro> emprestimoLivroList = new ArrayList<>();

}
