package services.usuarios.tests;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.Hooks;
import services.usuarios.payloads.PostUsuariosPayload;
import services.usuarios.requests.PostUsuariosRequest;
import utils.SchemaValidator;
import io.qameta.allure.Feature;

@DisplayName("Usuarios - POST /usuarios")
@Feature("Usuarios")
public class PostUsuariosTest extends Hooks {

    @Test
    @DisplayName("Validar cadastro de usuário com sucesso retornando mensagem e id")
    public void validarPostUsuariosComSucesso() {
        String payload = PostUsuariosPayload.payload();
        PostUsuariosRequest.executar(payload)
                .assertThat()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue());
    }

    @Test
    @DisplayName("Validar contrato da resposta de cadastro de usuário")
    public void validarSchemaPostUsuarios() {
        String payload = PostUsuariosPayload.payload();
        PostUsuariosRequest.executar(payload)
                .assertThat()
                .statusCode(201)
                .body(SchemaValidator.matchesSchema("services/usuarios/schema/PostUsuariosSchema.json"));
    }
}
