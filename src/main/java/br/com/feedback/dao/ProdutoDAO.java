package br.com.feedback.dao;

import br.com.feedback.model.Produto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de Produto — SOMENTE SQL e conversao ResultSet -> {@link Produto}.
 * Quem decide "quando" chamar cada metodo e o Service / Controller.
 */
public class ProdutoDAO extends MysqlDAO {

    public ProdutoDAO() {
        super();
    }

    /** READ (listar todos) — retorna todos os produtos, em ordem de nome. */
    public List<Produto> listarTodos() {
        String sql = "SELECT id, nome, descricao, preco FROM produtos ORDER BY nome";
        List<Produto> lista = new ArrayList<>();
        try (ResultSet rs = super.executar(sql)) {
            while (rs.next()) {
                lista.add(this.mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos.", e);
        }
        return lista;
    }

    /** READ (por id) — busca um produto especifico. */
    public Produto buscarPorId(Long id) {
        String sql = "SELECT id, nome, descricao, preco FROM produtos WHERE id = ?";
        try (ResultSet rs = super.executar(sql, id)) {
            if (rs.next()) {
                return this.mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto por id.", e);
        }
        return null;
    }

    /** CREATE — insere um novo produto. */
    public void inserir(Produto produto) {
        String sql = "INSERT INTO produtos (nome, descricao, preco) VALUES (?, ?, ?)";
        try {
            super.executarUpdate(
                    sql,
                    produto.getNome(),
                    produto.getDescricao(),
                    produto.getPreco());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir produto.", e);
        }
    }

    /** UPDATE — altera um produto existente. */
    public void alterar(Produto produto) {
        String sql = "UPDATE produtos SET nome = ?, descricao = ?, preco = ? WHERE id = ?";
        try {
            super.executarUpdate(
                    sql,
                    produto.getNome(),
                    produto.getDescricao(),
                    produto.getPreco(),
                    produto.getId());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar produto.", e);
        }
    }

    /** DELETE — exclui um produto pelo id. */
    public void deletar(Long id) {
        String sql = "DELETE FROM produtos WHERE id = ?";
        try {
            super.executarUpdate(sql, id);
        } catch (SQLIntegrityConstraintViolationException e) {
            // Chave estrangeira: existe feedback apontando para este produto.
            throw new IllegalArgumentException(
                    "Nao e possivel excluir: existe feedback vinculado a este produto.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar produto.", e);
        }
    }

    /** Converte uma linha do banco (ResultSet) em um objeto Produto. */
    private Produto mapear(ResultSet rs) throws SQLException {
        Produto produto = new Produto();
        produto.setId(rs.getLong("id"));
        produto.setNome(rs.getString("nome"));
        produto.setDescricao(rs.getString("descricao"));
        produto.setPreco(rs.getBigDecimal("preco"));
        return produto;
    }
}
