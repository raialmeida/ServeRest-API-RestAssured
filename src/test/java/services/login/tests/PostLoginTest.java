package services.login.tests;

import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import config.Hooks;
import services.login.payloads.PostLoginPayload;
import services.login.requests.PostLoginRequest;
import utils.SchemaValidator;
import io.qameta.allure.Feature;

@DisplayName("Login - POST /login")
@Feature("Login")
public class PostLoginTest extends Hooks {

    @Test
    @Tag("@smoke")
    @DisplayName("Validar login com sucesso retornando mensagem e token de autorização")
    public void validarPostLoginComSucesso() {
        String payload = PostLoginPayload.payload();
        PostLoginRequest.enviar(payload)
                .assertThat()
                .statusCode(200)
                .body("message", notNullValue())
                .body("authorization", notNullValue());
    }

    @Test
    @Tag("@smoke")
    @DisplayName("Validar contrato da resposta de login com sucesso")
    public void validarSchemaPostLogin() {
        String payload = PostLoginPayload.payload();
        PostLoginRequest.enviar(payload)
                .assertThat()
                .statusCode(200)
                .body(SchemaValidator.matchesSchema("services/login/schema/PostLoginSchema.json"));
    }
}
