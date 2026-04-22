package config;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import utils.UtilsUsuario;
import io.restassured.response.Response;

public class Hooks {
    protected static Response usuarioAdmin;

    @BeforeAll
    public static void setupBaseRequest() {
        RequestBase.baseRequest();
    }

    @BeforeEach
    public void setupAllure() {
        TestConfig.propertyAllure();
        if (usuarioAdmin == null) {
            usuarioAdmin = UtilsUsuario.criarUsuarioAdmin();
        }
    }
}
