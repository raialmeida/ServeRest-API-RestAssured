package br.com.serverest.services.usuarios.tests;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.serverest.hooks.Hooks;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.TmsLink;
import br.com.serverest.services.usuarios.payloads.PostUsuariosPayload;
import br.com.serverest.services.usuarios.requests.PostUsuariosRequest;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Usuarios - POST /usuarios")
@Feature("Usuarios")
public class PostUsuariosTest extends Hooks {

    @Test
    @TmsLink("384926")
    @Severity(CRITICAL)
    @DisplayName("Validar cadastro de usuário com sucesso retornando mensagem e id")
    public void validarPostUsuariosComSucesso() {
        String payload = PostUsuariosPayload.payload();
        PostUsuariosRequest.enviar(payload)
                .assertThat()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue());
    }

    @Test
    @TmsLink("395147")
    @Severity(CRITICAL)
    @DisplayName("Validar contrato da resposta de cadastro de usuário")
    public void validarSchemaPostUsuarios() {
        String payload = PostUsuariosPayload.payload();
        PostUsuariosRequest.enviar(payload)
                .assertThat()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("services/usuarios/schema/PostUsuariosSchema.json"));
    }
}
