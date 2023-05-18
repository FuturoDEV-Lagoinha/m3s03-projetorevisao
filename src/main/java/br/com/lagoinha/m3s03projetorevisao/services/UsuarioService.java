package br.com.lagoinha.m3s03projetorevisao.services;

import br.com.lagoinha.m3s03projetorevisao.entities.Usuario;
import br.com.lagoinha.m3s03projetorevisao.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Salvar / Consultar / Apagar

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario salvar(Usuario usuario) throws Exception {

        usuario.setBloqueado(false);

        // Se está editando
        if (usuario.getId() != null) {
            Usuario usuarioAntigo = buscarPorId(usuario.getId());
            usuario.setBloqueado(usuarioAntigo.getBloqueado());
        }

        if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
            throw new Exception("Nome é obrigatório!");
        }

        if (usuario.getDocumento() == null || usuario.getDocumento().isEmpty()) {
            throw new Exception("Documento é obrigatório!");
        }

        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new Exception("E-mail é obrigatório!");
        }

        usuario = usuarioRepository.save(usuario);

        return usuario;
    }

    public Usuario buscarPorId(Long id) throws Exception {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            throw new Exception("Usuario não encontrado!");
        }
        return usuarioOpt.get();
    }

    public boolean apagar(Long id) throws Exception {
        Usuario usuario = buscarPorId(id);
        try {
            usuarioRepository.delete(usuario);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
