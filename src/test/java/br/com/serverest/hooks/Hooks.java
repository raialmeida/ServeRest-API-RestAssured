package br.com.serverest.hooks;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import br.com.serverest.config.TestConfig;
import br.com.serverest.database.DatabaseConfig;
import br.com.serverest.utils.UtilsUsuario;
import io.restassured.response.Response;

public abstract class Hooks {

    protected static Response usuarioAdmin;

    @BeforeAll
    public static void globalSetup() {
        TestConfig.init();
    }

    @AfterAll
    public static void globalTearDown() throws SQLException {
        DatabaseConfig.fecharConexao();
    }

    @BeforeEach
    public void setupData() {
        if (usuarioAdmin == null) {
            usuarioAdmin = UtilsUsuario.criarUsuarioAdmin();
        }
    }
}
