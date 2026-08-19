package services.carrinhos.tests;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.Hooks;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.TmsLink;
import services.carrinhos.payloads.PostCarrinhosPayload;
import services.carrinhos.requests.DeleteCarrinhosCancelarCompraRequest;
import utils.SchemaValidator;
import utils.UtilsProduto;

@DisplayName("Carrinhos - DELETE /carrinhos/cancelar-compra")
@Feature("Carrinhos")
public class DeleteCarrinhosCancelarCompraTest extends Hooks {

    @Test
    @TmsLink("104582")
    @Severity(CRITICAL)
    @DisplayName("Validar cancelamento de compra com sucesso retornando mensagem de confirmação")
    public void validarDeleteCarrinhosCancelarCompraComSucesso() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        String payloadCarrinho = PostCarrinhosPayload.payload(idProduto);
        
        DeleteCarrinhosCancelarCompraRequest.enviarComCarrinho(payloadCarrinho)
                .assertThat()
                .statusCode(200)
                .body("message", containsString("sucesso"));
    }

    @Test
    @TmsLink("117349")
    @Severity(CRITICAL)
    @DisplayName("Validar contrato da resposta de cancelamento de compra")
    public void validarSchemaDeleteCarrinhosCancelarCompra() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        String payloadCarrinho = PostCarrinhosPayload.payload(idProduto);

        DeleteCarrinhosCancelarCompraRequest.enviarComCarrinho(payloadCarrinho)
                .assertThat()
                .statusCode(200)
                .body(SchemaValidator
                        .matchesSchema("DeleteCarrinhosCancelarCompraSchema.json"));
    }
}
