package br.com.lagoinha.m3s03projetorevisao.services;

import br.com.lagoinha.m3s03projetorevisao.entities.Emprestimo;
import br.com.lagoinha.m3s03projetorevisao.entities.EmprestimoLivro;
import br.com.lagoinha.m3s03projetorevisao.entities.Livro;
import br.com.lagoinha.m3s03projetorevisao.entities.Usuario;
import br.com.lagoinha.m3s03projetorevisao.repositories.EmprestimoRepository;
import br.com.lagoinha.m3s03projetorevisao.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LivroService livroService;

    public List<Emprestimo> buscarTodos() {
        return emprestimoRepository.findAll();
    }

    public Emprestimo buscarPorId(Long id) throws Exception {
        Optional<Emprestimo> emprestimoOpt = emprestimoRepository.findById(id);
        if (emprestimoOpt.isEmpty()) {
            throw new Exception("Empréstimo não encontrado!");
        }
        return emprestimoOpt.get();
    }

    public Emprestimo criar(Emprestimo emprestimo) throws Exception {

        emprestimo.setId(null);
        emprestimo.setDataEmprestimo(new Date());

        if (emprestimo.getUsuario() == null || emprestimo.getUsuario().getId() == null) {
            throw new Exception("Usuário não informado!");
        }
        Usuario usuario = usuarioService.buscarPorId(emprestimo.getUsuario().getId());
        if (usuario.getBloqueado()) {
            throw new Exception("Usuário está bloqueado!");
        }
        emprestimo.setUsuario(usuario);

        if (emprestimo.getEmprestimoLivroList() == null || emprestimo.getEmprestimoLivroList().isEmpty()) {
            throw new Exception("Nenhum livro sendo emprestado!");
        }

        for (EmprestimoLivro el : emprestimo.getEmprestimoLivroList()) {
            el.setEmprestimo(emprestimo);
            el.setDataDevolucao(null);

            if (el.getLivro() == null || el.getLivro().getId() == null) {
                throw new Exception("Livro não informado!");
            }
            Livro livro = livroService.buscarPorId(el.getLivro().getId());
            if (!livro.getDisponibilidade()) {
                throw new Exception(livro.getTitulo() + ": Livro não disponível para emprésimo!");
            }
            el.setLivro(livro);

            Date dataPrevista = DateUtil.somarDias(new Date(), 7);
            el.setDataPrevista(dataPrevista);

        }

        emprestimo = emprestimoRepository.save(emprestimo);

        // Bloquear usuário após criar empréstimo
        usuario.setBloqueado(true);

        // Diminui a quantidade de exemplares disponíveis
        for (EmprestimoLivro el : emprestimo.getEmprestimoLivroList()) {
            Livro livro = el.getLivro();
            livro.setExemplares(livro.getExemplares()-1);
            livro.setDisponibilidade(livro.getExemplares() > 0);
            livroService.salvar(livro);
        }

        return emprestimo;
    }

    public Emprestimo devolucao(Long usuarioId, Long livroId) throws Exception {
        List<Emprestimo> emprestimos = emprestimoRepository.findByUsuarioIdAndLivroIdDisponiveis(usuarioId, livroId);
        if (emprestimos == null || emprestimos.isEmpty()) {
            throw new Exception("Empréstimo não localizado!");
        }
        Emprestimo emprestimo = emprestimos.get(0);

        for (EmprestimoLivro el : emprestimo.getEmprestimoLivroList()) {
            if (livroId.equals(el.getLivro().getId()) && el.getDataDevolucao() == null) {
                el.setDataDevolucao(new Date());
                break;
            }
        }

        emprestimo = emprestimoRepository.save(emprestimo);

        // Devolveu o exemplar ao disponível
        Livro livro = livroService.buscarPorId(livroId);
        livro.setExemplares(livro.getExemplares()+1);
        livro.setDisponibilidade(livro.getExemplares() > 0);
        livroService.salvar(livro);

        // Desbloquear usuário após devolução
        List<Emprestimo> emprestimosPendentes = emprestimoRepository.findAllPendentes(usuarioId);
        if (emprestimosPendentes == null || emprestimosPendentes.isEmpty()) {
            Usuario usuario = usuarioService.buscarPorId(usuarioId);
            usuario.setBloqueado(false);
            usuarioService.salvar(usuario);
        }

        return emprestimo;
    }

}
