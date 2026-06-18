package services.carrinhos.tests;

import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.Hooks;
import services.carrinhos.payloads.PostCarrinhosPayload;
import services.carrinhos.requests.GetCarrinhosByIdRequest;
import services.carrinhos.requests.PostCarrinhosRequest;
import utils.SchemaValidator;
import utils.UtilsProduto;
import io.qameta.allure.Feature;

@DisplayName("Carrinhos - GET /carrinhos/{_id}")
@Feature("Carrinhos")
public class GetCarrinhosByIdTest extends Hooks {

    @Test
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
    @DisplayName("Validar contrato da resposta de consulta de carrinho por id")
    public void validarSchemaGetCarrinhosPorId() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        String payloadCarrinho = PostCarrinhosPayload.payload(idProduto);
        String idCarrinho = PostCarrinhosRequest.enviar(payloadCarrinho).extract().response().jsonPath()
                .getString("_id");

        GetCarrinhosByIdRequest.enviar(idCarrinho)
                .assertThat()
                .statusCode(200)
                .body(SchemaValidator.matchesSchema("services/carrinhos/schema/GetCarrinhosByIdSchema.json"));
    }
}
