package services.usuarios.tests;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.Hooks;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.TmsLink;
import services.usuarios.payloads.PutUsuariosByIdPayload;
import services.usuarios.requests.PutUsuariosByIdRequest;
import utils.SchemaValidator;
import utils.UtilsUsuario;

@DisplayName("Usuarios - PUT /usuarios/{_id}")
@Feature("Usuarios")
public class PutUsuariosByIdTest extends Hooks {

    @Test
    @TmsLink("406853")
    @Severity(CRITICAL)
    @DisplayName("Validar alteração de usuário com sucesso retornando mensagem de confirmação")
    public void validarPutUsuariosPorIdComSucesso() {
        String idUsuario = UtilsUsuario.criarUsuario(false).jsonPath().getString("_id");
        String payload = PutUsuariosByIdPayload.payload();
        PutUsuariosByIdRequest.enviar(idUsuario, payload)
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("Registro alterado com sucesso"));
    }

    @Test
    @TmsLink("417290")
    @Severity(CRITICAL)
    @DisplayName("Validar contrato da resposta de alteração de usuário")
    public void validarSchemaPutUsuariosPorId() {
        String idUsuario = UtilsUsuario.criarUsuario(false).jsonPath().getString("_id");
        String payload = PutUsuariosByIdPayload.payload();
        PutUsuariosByIdRequest.enviar(idUsuario, payload)
                .assertThat()
                .statusCode(200)
                .body(SchemaValidator.matchesSchema("PutUsuariosByIdSchema.json"));
    }
}
