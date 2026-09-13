package br.com.feedback.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SINGLETON de conexao com o MySQL.
 *
 * "Singleton" = padrao de projeto que garante UMA unica instancia desta classe
 * em todo o sistema. Assim reutilizamos a mesma conexao em vez de abrir varias.
 *
 * Oferece dois metodos para os DAOs:
 *  - executar()        -> para SELECT  (retorna ResultSet)
 *  - executarUpdate()  -> para INSERT / UPDATE / DELETE (retorna quantas linhas mudaram)
 */
public class MysqlSingleton {

    // Endereco do banco. "mysql" e o nome do servico no docker-compose.
    private static final String URL =
            "jdbc:mysql://mysql:3306/feedback_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "feedback_user";
    private static final String PASSWORD = "feedback123";

    private static MysqlSingleton instance;
    private Connection conexao;

    // Construtor PRIVADO: ninguem cria de fora, so a propria classe (regra do Singleton).
    private MysqlSingleton() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL nao encontrado no projeto.", e);
        }
    }

    // Ponto de acesso unico a instancia.
    public static synchronized MysqlSingleton getInstance() {
        if (instance == null) {
            instance = new MysqlSingleton();
        }
        return instance;
    }

    private Connection obterConexao() throws SQLException {
        if (this.conexao == null || this.conexao.isClosed()) {
            this.conexao = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return this.conexao;
    }

    // Usado para SELECT.
    public ResultSet executar(String sql, Object... parametros) throws SQLException {
        Connection conn = this.obterConexao();
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < parametros.length; i++) {
            ps.setObject(i + 1, parametros[i]);
        }
        return ps.executeQuery();
    }

    // Usado para INSERT / UPDATE / DELETE.
    public int executarUpdate(String sql, Object... parametros) throws SQLException {
        Connection conn = this.obterConexao();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < parametros.length; i++) {
                ps.setObject(i + 1, parametros[i]);
            }
            return ps.executeUpdate();
        }
    }
}
