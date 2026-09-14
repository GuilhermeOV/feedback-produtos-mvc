package br.com.feedback.dao;

import br.com.feedback.model.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de Usuario — SOMENTE SQL e conversao ResultSet -> {@link Usuario}.
 */
public class UsuarioDAO extends MysqlDAO {

    public UsuarioDAO() {
        super();
    }

    /** READ (listar todos). */
    public List<Usuario> listarTodos() {
        String sql = "SELECT id, nome, email FROM usuarios ORDER BY nome";
        List<Usuario> lista = new ArrayList<>();
        try (ResultSet rs = super.executar(sql)) {
            while (rs.next()) {
                lista.add(this.mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuarios.", e);
        }
        return lista;
    }

    /** READ (por id). */
    public Usuario buscarPorId(Long id) {
        String sql = "SELECT id, nome, email FROM usuarios WHERE id = ?";
        try (ResultSet rs = super.executar(sql, id)) {
            if (rs.next()) {
                return this.mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuario por id.", e);
        }
        return null;
    }

    /** READ (por email) — usado para garantir email unico. */
    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT id, nome, email FROM usuarios WHERE email = ?";
        try (ResultSet rs = super.executar(sql, email)) {
            if (rs.next()) {
                return this.mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuario por email.", e);
        }
        return null;
    }

    /** CREATE. */
    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, email) VALUES (?, ?)";
        try {
            super.executarUpdate(sql, usuario.getNome(), usuario.getEmail());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuario.", e);
        }
    }

    /** UPDATE. */
    public void alterar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nome = ?, email = ? WHERE id = ?";
        try {
            super.executarUpdate(sql, usuario.getNome(), usuario.getEmail(), usuario.getId());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar usuario.", e);
        }
    }

    /** DELETE. */
    public void deletar(Long id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try {
            super.executarUpdate(sql, id);
        } catch (SQLIntegrityConstraintViolationException e) {
            // Chave estrangeira: existe feedback apontando para este usuario.
            throw new IllegalArgumentException(
                    "Nao e possivel excluir: existe feedback vinculado a este usuario.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuario.", e);
        }
    }

    /** Converte uma linha do banco em um objeto Usuario. */
    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        return usuario;
    }
}
