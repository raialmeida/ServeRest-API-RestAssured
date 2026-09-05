package br.com.serverest.services.carrinhos.tests;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.serverest.hooks.Hooks;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.TmsLink;
import br.com.serverest.services.carrinhos.payloads.PostCarrinhosPayload;
import br.com.serverest.services.carrinhos.requests.GetCarrinhosByIdRequest;
import br.com.serverest.services.carrinhos.requests.PostCarrinhosRequest;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import br.com.serverest.utils.UtilsProduto;

@DisplayName("Carrinhos - GET /carrinhos/{_id}")
@Feature("Carrinhos")
public class GetCarrinhosByIdTest extends Hooks {

    @Test
    @TmsLink("145907")
    @Severity(CRITICAL)
    @DisplayName("Verificar consulta de carrinho por id retornando o mesmo id solicitado")
    public void validarGetCarrinhosPorIdComSucesso() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        String payloadCarrinho = PostCarrinhosPayload.payload(idProduto);
        String idCarrinho = PostCarrinhosRequest.enviar(payloadCarrinho).extract().response().jsonPath()
                .getString("_id");

        GetCarrinhosByIdRequest.enviar(idCarrinho)
                .assertThat()
                .statusCode(200)
                .body("_id", equalTo(idCarrinho));
    }

    @Test
    @TmsLink("156234")
    @Severity(CRITICAL)
    @DisplayName("Validar contrato da resposta de consulta de carrinho por id")
    public void validarSchemaGetCarrinhosPorId() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        String payloadCarrinho = PostCarrinhosPayload.payload(idProduto);
        String idCarrinho = PostCarrinhosRequest.enviar(payloadCarrinho).extract().response().jsonPath()
                .getString("_id");

        GetCarrinhosByIdRequest.enviar(idCarrinho)
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("services/carrinhos/schema/GetCarrinhosByIdSchema.json"));
    }
}
