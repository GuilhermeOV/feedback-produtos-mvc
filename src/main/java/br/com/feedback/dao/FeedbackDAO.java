package br.com.feedback.dao;

import br.com.feedback.model.Feedback;
import br.com.feedback.model.Produto;
import br.com.feedback.model.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de Feedback — SOMENTE SQL e conversao ResultSet -> {@link Feedback}.
 *
 * Usa INNER JOIN com "usuarios" e "produtos" para trazer, junto com o feedback,
 * o nome do usuario e o nome do produto (e nao apenas os ids).
 */
public class FeedbackDAO extends MysqlDAO {

    // SELECT base reutilizado na listagem e na busca por id.
    private static final String SELECT_BASE =
            "SELECT f.id, f.usuario_id, f.produto_id, f.nota, f.comentario, "
                    + "u.nome AS usuario_nome, u.email AS usuario_email, "
                    + "p.nome AS produto_nome "
                    + "FROM feedback f "
                    + "INNER JOIN usuarios u ON u.id = f.usuario_id "
                    + "INNER JOIN produtos p ON p.id = f.produto_id ";

    public FeedbackDAO() {
        super();
    }

    /** READ (listar todos). */
    public List<Feedback> listarTodos() {
        String sql = SELECT_BASE + "ORDER BY f.id";
        List<Feedback> lista = new ArrayList<>();
        try (ResultSet rs = super.executar(sql)) {
            while (rs.next()) {
                lista.add(this.mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar feedbacks.", e);
        }
        return lista;
    }

    /** READ (por id). */
    public Feedback buscarPorId(Long id) {
        String sql = SELECT_BASE + "WHERE f.id = ?";
        try (ResultSet rs = super.executar(sql, id)) {
            if (rs.next()) {
                return this.mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar feedback por id.", e);
        }
        return null;
    }

    /** CREATE. */
    public void inserir(Feedback feedback) {
        String sql = "INSERT INTO feedback (usuario_id, produto_id, nota, comentario) VALUES (?, ?, ?, ?)";
        try {
            super.executarUpdate(
                    sql,
                    feedback.getUsuarioId(),
                    feedback.getProdutoId(),
                    feedback.getNota(),
                    feedback.getComentario());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir feedback.", e);
        }
    }

    /** UPDATE. */
    public void alterar(Feedback feedback) {
        String sql = "UPDATE feedback SET usuario_id = ?, produto_id = ?, nota = ?, comentario = ? WHERE id = ?";
        try {
            super.executarUpdate(
                    sql,
                    feedback.getUsuarioId(),
                    feedback.getProdutoId(),
                    feedback.getNota(),
                    feedback.getComentario(),
                    feedback.getId());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar feedback.", e);
        }
    }

    /** DELETE. */
    public void deletar(Long id) {
        String sql = "DELETE FROM feedback WHERE id = ?";
        try {
            super.executarUpdate(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar feedback.", e);
        }
    }

    /** Converte uma linha (com os dados juntados) em um objeto Feedback completo. */
    private Feedback mapear(ResultSet rs) throws SQLException {
        Feedback feedback = new Feedback();
        feedback.setId(rs.getLong("id"));
        feedback.setUsuarioId(rs.getLong("usuario_id"));
        feedback.setProdutoId(rs.getLong("produto_id"));
        feedback.setNota(rs.getInt("nota"));
        feedback.setComentario(rs.getString("comentario"));

        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("usuario_id"));
        usuario.setNome(rs.getString("usuario_nome"));
        usuario.setEmail(rs.getString("usuario_email"));
        feedback.setUsuario(usuario);

        Produto produto = new Produto();
        produto.setId(rs.getLong("produto_id"));
        produto.setNome(rs.getString("produto_nome"));
        feedback.setProduto(produto);

        return feedback;
    }
}
