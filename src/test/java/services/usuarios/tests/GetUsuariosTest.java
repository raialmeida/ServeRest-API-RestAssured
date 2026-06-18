package services.usuarios.tests;

import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.Hooks;
import services.usuarios.requests.GetUsuariosRequest;
import utils.SchemaValidator;
import io.qameta.allure.Feature;

@DisplayName("Usuarios - GET /usuarios")
@Feature("Usuarios")
public class GetUsuariosTest extends Hooks {

    @Test
    @DisplayName("Verificar listagem de usuários retornando lista não nula")
    public void validarGetUsuariosComSucesso() {

        GetUsuariosRequest.enviar()
                .assertThat()
                .statusCode(200)
                .body("usuarios", notNullValue());
    }

    @Test
    @DisplayName("Validar contrato da resposta de listagem de usuários")
    public void validarSchemaGetUsuarios() {

        GetUsuariosRequest.enviar()
                .assertThat()
                .statusCode(200)
                .body(SchemaValidator.matchesSchema("services/usuarios/schema/GetUsuariosSchema.json"));
    }
}
