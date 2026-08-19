package services.usuarios.tests;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.Hooks;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.TmsLink;
import services.usuarios.requests.DeleteUsuariosByIdRequest;
import utils.SchemaValidator;
import utils.UtilsUsuario;

@DisplayName("Usuarios - DELETE /usuarios/{_id}")
@Feature("Usuarios")
public class DeleteUsuariosByIdTest extends Hooks {

    @Test
    @TmsLink("327946")
    @Severity(CRITICAL)
    @DisplayName("Validar exclusão de usuário com sucesso retornando mensagem de confirmação")
    public void validarDeleteUsuariosPorIdComSucesso() {
        String idUsuario = UtilsUsuario.criarUsuario(false).jsonPath().getString("_id");
        DeleteUsuariosByIdRequest.enviar(idUsuario)
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("Registro excluído com sucesso"));
    }

    @Test
    @TmsLink("338571")
    @Severity(CRITICAL)
    @DisplayName("Validar contrato da resposta de exclusão de usuário")
    public void validarSchemaDeleteUsuariosPorId() {
        String idUsuario = UtilsUsuario.criarUsuario(false).jsonPath().getString("_id");
        DeleteUsuariosByIdRequest.enviar(idUsuario)
                .assertThat()
                .statusCode(200)
                .body(SchemaValidator.matchesSchema("DeleteUsuariosByIdSchema.json"));
    }
}
