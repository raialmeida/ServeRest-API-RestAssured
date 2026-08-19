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
import services.carrinhos.requests.DeleteCarrinhosConcluirCompraRequest;
import utils.SchemaValidator;
import utils.UtilsProduto;

@DisplayName("Carrinhos - DELETE /carrinhos/concluir-compra")
@Feature("Carrinhos")
public class DeleteCarrinhosConcluirCompraTest extends Hooks {

    @Test
    @TmsLink("128761")
    @Severity(CRITICAL)
    @DisplayName("Validar conclusão de compra com sucesso retornando mensagem de confirmação")
    public void validarDeleteCarrinhosConcluirCompraComSucesso() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        String payloadCarrinho = PostCarrinhosPayload.payload(idProduto);
        
        DeleteCarrinhosConcluirCompraRequest.enviarComCarrinho(payloadCarrinho)
                .assertThat()
                .statusCode(200)
                .body("message", containsString("sucesso"));
    }

    @Test
    @TmsLink("139428")
    @Severity(CRITICAL)
    @DisplayName("Validar contrato da resposta de conclusão de compra")
    public void validarSchemaDeleteCarrinhosConcluirCompra() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        String payloadCarrinho = PostCarrinhosPayload.payload(idProduto);

        DeleteCarrinhosConcluirCompraRequest.enviarComCarrinho(payloadCarrinho)
                .assertThat()
                .statusCode(200)
                .body(SchemaValidator
                        .matchesSchema("DeleteCarrinhosConcluirCompraSchema.json"));
    }
}
