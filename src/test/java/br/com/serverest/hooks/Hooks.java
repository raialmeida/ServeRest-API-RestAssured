package br.com.serverest.hooks;

import br.com.serverest.config.TestConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import io.restassured.response.Response;
import br.com.serverest.utils.UtilsUsuario;

public abstract class Hooks {

    protected static Response usuarioAdmin;

    @BeforeAll
    public static void globalSetup() {
        TestConfig.init();
    }

    @BeforeEach
    public void setupData() {
        if (usuarioAdmin == null) {
            usuarioAdmin = UtilsUsuario.criarUsuarioAdmin();
        }
    }
}
