package br.com.serverest.services.usuarios.tests;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.serverest.hooks.Hooks;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.TmsLink;
import br.com.serverest.services.usuarios.requests.GetUsuariosRequest;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Usuarios - GET /usuarios")
@Feature("Usuarios")
public class GetUsuariosTest extends Hooks {

    @Test
    @TmsLink("362719")
    @Severity(CRITICAL)
    @DisplayName("Verificar listagem de usuários retornando lista não nula")
    public void validarGetUsuariosComSucesso() {

        GetUsuariosRequest.enviar()
                .assertThat()
                .statusCode(200)
                .body("usuarios", notNullValue());
    }

    @Test
    @TmsLink("373485")
    @Severity(CRITICAL)
    @DisplayName("Validar contrato da resposta de listagem de usuários")
    public void validarSchemaGetUsuarios() {

        GetUsuariosRequest.enviar()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("services/usuarios/schema/GetUsuariosSchema.json"));
    }
}
