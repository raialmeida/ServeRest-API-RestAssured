package services.usuarios.tests;

import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.Hooks;
import services.usuarios.requests.GetUsuariosByIdRequest;
import utils.SchemaValidator;
import utils.UtilsUsuario;
import io.qameta.allure.Feature;

@DisplayName("Usuarios - GET /usuarios/{_id}")
@Feature("Usuarios")
public class GetUsuariosByIdTest extends Hooks {

    @Test
    @DisplayName("Verificar consulta de usuário por id retornando o mesmo id solicitado")
    public void validarGetUsuariosPorIdComSucesso() {
        String idUsuario = UtilsUsuario.criarUsuario(false).jsonPath().getString("_id");
        GetUsuariosByIdRequest.executar(idUsuario)
                .assertThat()
                .statusCode(200)
                .body("_id", equalTo(idUsuario));
    }

    @Test
    @DisplayName("Validar contrato da resposta de consulta de usuário por id")
    public void validarSchemaGetUsuariosPorId() {
        String idUsuario = UtilsUsuario.criarUsuario(false).jsonPath().getString("_id");
        GetUsuariosByIdRequest.executar(idUsuario)
                .assertThat()
                .statusCode(200)
                .body(SchemaValidator.matchesSchema("services/usuarios/schema/GetUsuariosByIdSchema.json"));
    }
}
