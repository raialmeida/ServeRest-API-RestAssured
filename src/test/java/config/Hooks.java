package config;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import io.restassured.response.Response;
import utils.UtilsUsuario;

public class Hooks {
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
