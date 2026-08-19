package services.login.tests;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import config.Hooks;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.TmsLink;
import services.login.payloads.PostLoginPayload;
import services.login.requests.PostLoginRequest;
import utils.SchemaValidator;

@DisplayName("Login - POST /login")
@Feature("Login")
public class PostLoginTest extends Hooks {

    @Test
    @TmsLink("204817")
    @Severity(CRITICAL)
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
    @TmsLink("215693")
    @Severity(CRITICAL)
    @Tag("@smoke")
    @DisplayName("Validar contrato da resposta de login com sucesso")
    public void validarSchemaPostLogin() {
        String payload = PostLoginPayload.payload();
        PostLoginRequest.enviar(payload)
                .assertThat()
                .statusCode(200)
                .body(SchemaValidator.matchesSchema("PostLoginSchema.json"));
    }
}
