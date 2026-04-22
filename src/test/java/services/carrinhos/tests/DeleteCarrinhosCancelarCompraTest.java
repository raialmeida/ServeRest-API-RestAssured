package services.carrinhos.tests;

import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.Hooks;
import services.carrinhos.payloads.PostCarrinhosPayload;
import services.carrinhos.requests.DeleteCarrinhosCancelarCompraRequest;
import utils.SchemaValidator;
import utils.UtilsProduto;
import io.qameta.allure.Feature;

@DisplayName("Carrinhos - DELETE /carrinhos/cancelar-compra")
@Feature("Carrinhos")
public class DeleteCarrinhosCancelarCompraTest extends Hooks {

    @Test
    @DisplayName("Validar cancelamento de compra com sucesso retornando mensagem de confirmação")
    public void validarDeleteCarrinhosCancelarCompraComSucesso() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        String payloadCarrinho = PostCarrinhosPayload.payload(idProduto);
        
        DeleteCarrinhosCancelarCompraRequest.executarComCarrinho(payloadCarrinho)
                .assertThat()
                .statusCode(200)
                .body("message", containsString("sucesso"));
    }

    @Test
    @DisplayName("Validar contrato da resposta de cancelamento de compra")
    public void validarSchemaDeleteCarrinhosCancelarCompra() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        String payloadCarrinho = PostCarrinhosPayload.payload(idProduto);

        DeleteCarrinhosCancelarCompraRequest.executarComCarrinho(payloadCarrinho)
                .assertThat()
                .statusCode(200)
                .body(SchemaValidator
                        .matchesSchema("services/carrinhos/schema/DeleteCarrinhosCancelarCompraSchema.json"));
    }
}
