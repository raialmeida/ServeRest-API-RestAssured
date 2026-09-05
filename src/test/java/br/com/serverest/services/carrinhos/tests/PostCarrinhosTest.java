package br.com.serverest.services.carrinhos.tests;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import br.com.serverest.hooks.Hooks;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.TmsLink;
import br.com.serverest.services.carrinhos.payloads.PostCarrinhosPayload;
import br.com.serverest.services.carrinhos.requests.PostCarrinhosRequest;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import br.com.serverest.utils.UtilsProduto;

@DisplayName("Carrinhos - POST /carrinhos")
@Feature("Carrinhos")
public class PostCarrinhosTest extends Hooks {

    @Test
    @TmsLink("189731")
    @Severity(CRITICAL)
    @Tag("smoke")
    @DisplayName("Validar cadastro de carrinho com sucesso retornando mensagem e id")
    public void validarPostCarrinhosComSucesso() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        String payload = PostCarrinhosPayload.payload(idProduto);
        
        PostCarrinhosRequest.enviar(payload)
                .assertThat()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue());
    }

    @Test
    @TmsLink("193546")
    @Severity(CRITICAL)
    @DisplayName("Validar contrato da resposta de cadastro de carrinho")
    public void validarSchemaPostCarrinhos() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        String payload = PostCarrinhosPayload.payload(idProduto);

        PostCarrinhosRequest.enviar(payload)
                .assertThat()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("services/carrinhos/schema/PostCarrinhosSchema.json"));
    }
}
