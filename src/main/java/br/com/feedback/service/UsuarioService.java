package br.com.feedback.service;

import br.com.feedback.dao.UsuarioDAO;
import br.com.feedback.model.Usuario;

import java.util.List;

/**
 * SERVICE de Usuario — regras de negocio.
 */
public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public List<Usuario> listar() {
        return this.usuarioDAO.listarTodos();
    }

    public Usuario buscarPorId(Long id) {
        if (id == null) {
            return null;
        }
        return this.usuarioDAO.buscarPorId(id);
    }

    /**
     * Regra de salvamento:
     * - sem id -> cadastro novo
     * - com id -> alteracao (usuario precisa existir)
     */
    public void salvar(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario e obrigatorio.");
        }

        usuario.setNome(this.normalizar(usuario.getNome()));
        usuario.setEmail(this.normalizar(usuario.getEmail()));

        this.validarCampos(usuario);
        this.validarEmailUnico(usuario);

        if (usuario.getId() == null) {
            this.usuarioDAO.inserir(usuario);
            return;
        }

        if (this.usuarioDAO.buscarPorId(usuario.getId()) == null) {
            throw new IllegalArgumentException("Usuario nao encontrado para alteracao.");
        }
        this.usuarioDAO.alterar(usuario);
    }

    /**
     * Regra de exclusao:
     * - id obrigatorio
     * - usuario precisa existir
     */
    public void deletar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id e obrigatorio para excluir.");
        }
        if (this.usuarioDAO.buscarPorId(id) == null) {
            throw new IllegalArgumentException("Usuario nao encontrado.");
        }
        this.usuarioDAO.deletar(id);
    }

    private void validarCampos(Usuario usuario) {
        if (usuario.getNome() == null) {
            throw new IllegalArgumentException("Nome e obrigatorio.");
        }
        if (usuario.getEmail() == null) {
            throw new IllegalArgumentException("Email e obrigatorio.");
        }
        if (!usuario.getEmail().contains("@") || !usuario.getEmail().contains(".")) {
            throw new IllegalArgumentException("Email invalido (ex: nome@email.com).");
        }
    }

    /**
     * Garante que nao existe outro usuario com o mesmo email.
     * - no cadastro: qualquer email repetido e invalido
     * - na alteracao: so permite se o email for do proprio usuario
     */
    private void validarEmailUnico(Usuario usuario) {
        Usuario existente = this.usuarioDAO.buscarPorEmail(usuario.getEmail());
        if (existente == null) {
            return;
        }
        if (usuario.getId() == null) {
            throw new IllegalArgumentException("Ja existe um usuario com este email.");
        }
        if (!existente.getId().equals(usuario.getId())) {
            throw new IllegalArgumentException("Ja existe um usuario com este email.");
        }
    }

    private String normalizar(String valor) {
        if (valor == null) {
            return null;
        }
        String limpo = valor.trim();
        return limpo.isEmpty() ? null : limpo;
    }
}
