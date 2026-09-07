package br.com.serverest.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import br.com.serverest.config.Environment;

public final class DatabaseConfig {

    private static Connection connection;

    private DatabaseConfig() {
    }

    /**
     * Abre a conexão quando necessário e a reutiliza até o fim da classe de testes.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            Properties propriedades = new Properties();
            propriedades.setProperty("databaseName", Environment.getEnv("DB_NAME"));
            propriedades.setProperty("user", Environment.getEnv("DB_USER"));
            propriedades.setProperty("password", Environment.getEnv("DB_PASSWORD"));
            connection = DriverManager.getConnection(Environment.getEnv("DB_URL"), propriedades);
        }
        return connection;
    }

    /** Fecha a conexão no AfterAll da classe de testes. */
    public static void fecharConexao() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    /**
     * Executa INSERT, UPDATE ou DELETE parametrizado e retorna as linhas afetadas.
     */
    public static int executeUpdate(String sql, Object... parametros) {
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            preencherParametros(statement, parametros);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao executar alteração no banco de dados." + e.getMessage(), e);
        }
    }

    /**
     * Retorna todas as linhas da consulta; sem resultados, retorna uma lista vazia.
     */
    public static List<Map<String, Object>> queryConsultar(String sql, Object... parametros) {
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            preencherParametros(statement, parametros);
            try (ResultSet result = statement.executeQuery()) {
                List<Map<String, Object>> registros = new ArrayList<>();
                ResultSetMetaData metadata = result.getMetaData();
                while (result.next()) {
                    Map<String, Object> registro = new LinkedHashMap<>();
                    for (int coluna = 1; coluna <= metadata.getColumnCount(); coluna++) {
                        registro.put(metadata.getColumnLabel(coluna), result.getObject(coluna));
                    }
                    registros.add(registro);
                }
                return registros;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao consultar o banco de dados: " + e.getMessage(), e);
        }
    }

    private static void preencherParametros(PreparedStatement statement, Object... parametros) throws SQLException {
        for (int indice = 0; indice < parametros.length; indice++) {
            statement.setObject(indice + 1, parametros[indice]);
        }
    }
}
