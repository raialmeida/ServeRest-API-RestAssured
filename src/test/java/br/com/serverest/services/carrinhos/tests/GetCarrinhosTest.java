package br.com.serverest.services.carrinhos.tests;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.serverest.hooks.Hooks;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.TmsLink;
import br.com.serverest.services.carrinhos.requests.GetCarrinhosRequest;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Carrinhos - GET /carrinhos")
@Feature("Carrinhos")
public class GetCarrinhosTest extends Hooks {

    @Test
    @TmsLink("167895")
    @Severity(CRITICAL)
    @DisplayName("Verificar listagem de carrinhos retornando lista não nula")
    public void validarGetCarrinhosComSucesso() {

        GetCarrinhosRequest.enviar()
                .assertThat()
                .statusCode(200)
                .body("carrinhos", notNullValue());
    }

    @Test
    @TmsLink("178462")
    @Severity(CRITICAL)
    @DisplayName("Validar contrato da resposta de listagem de carrinhos")
    public void validarSchemaGetCarrinhos() {

        GetCarrinhosRequest.enviar()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("services/carrinhos/schema/GetCarrinhosSchema.json"));
    }
}
