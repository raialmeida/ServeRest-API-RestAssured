package services.usuarios.tests;

import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.Hooks;
import services.usuarios.requests.DeleteUsuariosByIdRequest;
import utils.SchemaValidator;
import utils.UtilsUsuario;
import io.qameta.allure.Feature;

@DisplayName("Usuarios - DELETE /usuarios/{_id}")
@Feature("Usuarios")
public class DeleteUsuariosByIdTest extends Hooks {

    @Test
    @DisplayName("Validar exclusão de usuário com sucesso retornando mensagem de confirmação")
    public void validarDeleteUsuariosPorIdComSucesso() {
        String idUsuario = UtilsUsuario.criarUsuario(false).jsonPath().getString("_id");
        DeleteUsuariosByIdRequest.enviar(idUsuario)
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("Registro excluído com sucesso"));
    }

    @Test
    @DisplayName("Validar contrato da resposta de exclusão de usuário")
    public void validarSchemaDeleteUsuariosPorId() {
        String idUsuario = UtilsUsuario.criarUsuario(false).jsonPath().getString("_id");
        DeleteUsuariosByIdRequest.enviar(idUsuario)
                .assertThat()
                .statusCode(200)
                .body(SchemaValidator.matchesSchema("services/usuarios/schema/DeleteUsuariosByIdSchema.json"));
    }
}
