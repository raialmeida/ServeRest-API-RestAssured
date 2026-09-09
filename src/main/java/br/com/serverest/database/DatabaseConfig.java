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

import org.postgresql.ds.PGSimpleDataSource;

import br.com.serverest.config.Environment;

public final class DatabaseConfig {

    private static Connection postgresConnection;
    private static Connection sqlServerConnection;
    private static String postgresBase;
    private static String sqlServerBase;

    private DatabaseConfig() {
    }

    /**
     * Abre e reutiliza a conexão PostgreSQL para a base informada.
     */
    private static Connection getPostgresConnection(String nomeBase) throws SQLException {
        if (nomeBase == null || nomeBase.isBlank()) {
            throw new IllegalArgumentException("Informe o nome da base.");
        }
        if (postgresConnection == null || postgresConnection.isClosed() || !nomeBase.equals(postgresBase)) {
            if (postgresConnection != null) {
                postgresConnection.close();
            }
            PGSimpleDataSource dataSource = new PGSimpleDataSource();
            dataSource.setUrl(Environment.getEnv("POSTGRES_DB_URL"));
            dataSource.setDatabaseName(nomeBase);
            dataSource.setUser(Environment.getEnv("POSTGRES_DB_USER"));
            dataSource.setPassword(Environment.getEnv("POSTGRES_DB_PASSWORD"));
            postgresConnection = dataSource.getConnection();
            postgresBase = nomeBase;
        }
        return postgresConnection;
    }

    /** Abre e reutiliza a conexão SQL Server para a base informada. */
    private static Connection getSqlServerConnection(String nomeBase) throws SQLException {
        if (nomeBase == null || nomeBase.isBlank()) {
            throw new IllegalArgumentException("Informe o nome da base.");
        }
        if (sqlServerConnection == null || sqlServerConnection.isClosed() || !nomeBase.equals(sqlServerBase)) {
            if (sqlServerConnection != null) {
                sqlServerConnection.close();
            }
            Properties propriedades = new Properties();
            propriedades.setProperty("databaseName", nomeBase);
            propriedades.setProperty("user", Environment.getEnv("SQLSERVER_DB_USER"));
            propriedades.setProperty("password", Environment.getEnv("SQLSERVER_DB_PASSWORD"));
            sqlServerConnection = DriverManager.getConnection(Environment.getEnv("SQLSERVER_DB_URL"), propriedades);
            sqlServerBase = nomeBase;
        }
        return sqlServerConnection;
    }

    /** Fecha as duas conexões no AfterAll, mesmo se o fechamento de uma falhar. */
    public static void fecharConexao() throws SQLException {
        try (Connection postgres = postgresConnection; Connection sqlServer = sqlServerConnection) {
            postgresConnection = null;
            sqlServerConnection = null;
            postgresBase = null;
            sqlServerBase = null;
        }
    }

    /**
     * Executa INSERT, UPDATE ou DELETE usando POSTGRES_DB_NAME do ambiente.
     * Retorna a quantidade de linhas afetadas.
     */
    public static int postgresExecuteUpdate(String sql, Object... parametros) {
        return postgresExecuteUpdate(Environment.getEnv("POSTGRES_DB_NAME"), sql, parametros);
    }

    /**
     * Executa INSERT, UPDATE ou DELETE na base PostgreSQL informada.
     * Retorna a quantidade de linhas afetadas.
     */
    public static int postgresExecuteUpdate(String nomeBase, String sql, Object... parametros) {
        try (PreparedStatement statement = getPostgresConnection(nomeBase).prepareStatement(sql)) {
            for (int indice = 0; indice < parametros.length; indice++) {
                statement.setObject(indice + 1, parametros[indice]);
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao executar alteração no banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Executa INSERT, UPDATE ou DELETE usando SQLSERVER_DB_NAME do ambiente.
     * Retorna a quantidade de linhas afetadas.
     */
    public static int sqlServerExecuteUpdate(String sql, Object... parametros) {
        return sqlServerExecuteUpdate(Environment.getEnv("SQLSERVER_DB_NAME"), sql, parametros);
    }

    /**
     * Executa INSERT, UPDATE ou DELETE na base SQL Server informada.
     * Retorna a quantidade de linhas afetadas.
     */
    public static int sqlServerExecuteUpdate(String nomeBase, String sql, Object... parametros) {
        try (PreparedStatement statement = getSqlServerConnection(nomeBase).prepareStatement(sql)) {
            for (int indice = 0; indice < parametros.length; indice++) {
                statement.setObject(indice + 1, parametros[indice]);
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao executar alteração no banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Consulta a base definida em POSTGRES_DB_NAME.
     * Retorna uma lista de registros ou uma lista vazia quando não houver resultados.
     */
    public static List<Map<String, Object>> postgresQuery(String sql, Object... parametros) {
        return postgresQuery(Environment.getEnv("POSTGRES_DB_NAME"), sql, parametros);
    }

    /**
     * Consulta a base PostgreSQL informada.
     * Retorna uma lista de registros ou uma lista vazia quando não houver resultados.
     */
    public static List<Map<String, Object>> postgresQuery(String nomeBase, String sql, Object... parametros) {
        try {
            return consultar(getPostgresConnection(nomeBase), sql, parametros);
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao consultar o banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Consulta a base definida em SQLSERVER_DB_NAME.
     * Retorna uma lista de registros ou uma lista vazia quando não houver resultados.
     */
    public static List<Map<String, Object>> sqlServerQuery(String sql, Object... parametros) {
        return sqlServerQuery(Environment.getEnv("SQLSERVER_DB_NAME"), sql, parametros);
    }

    /**
     * Consulta a base SQL Server informada.
     * Retorna uma lista de registros ou uma lista vazia quando não houver resultados.
     */
    public static List<Map<String, Object>> sqlServerQuery(String nomeBase, String sql, Object... parametros) {
        try {
            return consultar(getSqlServerConnection(nomeBase), sql, parametros);
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao consultar o banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Executa a consulta com os parâmetros e retorna cada linha como um mapa.
     */
    private static List<Map<String, Object>> consultar(Connection conexao, String sql, Object... parametros)
            throws SQLException {
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            for (int indice = 0; indice < parametros.length; indice++) {
                statement.setObject(indice + 1, parametros[indice]);
            }
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
        }
    }
}
