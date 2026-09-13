package br.com.feedback.dao;

import br.com.feedback.config.MysqlSingleton;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe BASE dos DAOs que usam MySQL.
 *
 * DAO = Data Access Object (Objeto de Acesso a Dados).
 * Guarda a referencia ao {@link MysqlSingleton} (a conexao reutilizada).
 * Os DAOs filhos (ProdutoDAO, UsuarioDAO, FeedbackDAO) herdam daqui e usam:
 *  - executar()       -> para SELECT
 *  - executarUpdate() -> para INSERT / UPDATE / DELETE
 */
public class MysqlDAO {

    protected final MysqlSingleton banco;

    public MysqlDAO() {
        this.banco = MysqlSingleton.getInstance();
    }

    protected ResultSet executar(String sql, Object... parametros) throws SQLException {
        return this.banco.executar(sql, parametros);
    }

    protected int executarUpdate(String sql, Object... parametros) throws SQLException {
        return this.banco.executarUpdate(sql, parametros);
    }
}
