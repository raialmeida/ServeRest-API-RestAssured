package services.usuarios.tests;

import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.Hooks;
import services.usuarios.payloads.PutUsuariosByIdPayload;
import services.usuarios.requests.PutUsuariosByIdRequest;
import utils.SchemaValidator;
import utils.UtilsUsuario;
import io.qameta.allure.Feature;

@DisplayName("Usuarios - PUT /usuarios/{_id}")
@Feature("Usuarios")
public class PutUsuariosByIdTest extends Hooks {

    @Test
    @DisplayName("Validar alteração de usuário com sucesso retornando mensagem de confirmação")
    public void validarPutUsuariosPorIdComSucesso() {
        String idUsuario = UtilsUsuario.criarUsuario(false).jsonPath().getString("_id");
        String payload = PutUsuariosByIdPayload.payload();
        PutUsuariosByIdRequest.executar(idUsuario, payload)
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("Registro alterado com sucesso"));
    }

    @Test
    @DisplayName("Validar contrato da resposta de alteração de usuário")
    public void validarSchemaPutUsuariosPorId() {
        String idUsuario = UtilsUsuario.criarUsuario(false).jsonPath().getString("_id");
        String payload = PutUsuariosByIdPayload.payload();
        PutUsuariosByIdRequest.executar(idUsuario, payload)
                .assertThat()
                .statusCode(200)
                .body(SchemaValidator.matchesSchema("services/usuarios/schema/PutUsuariosByIdSchema.json"));
    }
}
