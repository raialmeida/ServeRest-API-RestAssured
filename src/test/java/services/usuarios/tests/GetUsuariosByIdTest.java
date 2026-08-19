package services.usuarios.tests;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.Hooks;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.TmsLink;
import services.usuarios.requests.GetUsuariosByIdRequest;
import utils.SchemaValidator;
import utils.UtilsUsuario;

@DisplayName("Usuarios - GET /usuarios/{_id}")
@Feature("Usuarios")
public class GetUsuariosByIdTest extends Hooks {

    @Test
    @TmsLink("349208")
    @Severity(CRITICAL)
    @DisplayName("Verificar consulta de usuário por id retornando o mesmo id solicitado")
    public void validarGetUsuariosPorIdComSucesso() {
        String idUsuario = UtilsUsuario.criarUsuario(false).jsonPath().getString("_id");
        GetUsuariosByIdRequest.enviar(idUsuario)
                .assertThat()
                .statusCode(200)
                .body("_id", equalTo(idUsuario));
    }

    @Test
    @TmsLink("351864")
    @Severity(CRITICAL)
    @DisplayName("Validar contrato da resposta de consulta de usuário por id")
    public void validarSchemaGetUsuariosPorId() {
        String idUsuario = UtilsUsuario.criarUsuario(false).jsonPath().getString("_id");
        GetUsuariosByIdRequest.enviar(idUsuario)
                .assertThat()
                .statusCode(200)
                .body(SchemaValidator.matchesSchema("GetUsuariosByIdSchema.json"));
    }
}
