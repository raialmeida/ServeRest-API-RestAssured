package services.carrinhos.tests;

import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.Hooks;
import services.carrinhos.payloads.PostCarrinhosPayload;
import services.carrinhos.requests.DeleteCarrinhosConcluirCompraRequest;
import utils.SchemaValidator;
import utils.UtilsProduto;
import io.qameta.allure.Feature;

@DisplayName("Carrinhos - DELETE /carrinhos/concluir-compra")
@Feature("Carrinhos")
public class DeleteCarrinhosConcluirCompraTest extends Hooks {

    @Test
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
    @DisplayName("Validar contrato da resposta de conclusão de compra")
    public void validarSchemaDeleteCarrinhosConcluirCompra() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        String payloadCarrinho = PostCarrinhosPayload.payload(idProduto);

        DeleteCarrinhosConcluirCompraRequest.enviarComCarrinho(payloadCarrinho)
                .assertThat()
                .statusCode(200)
                .body(SchemaValidator
                        .matchesSchema("services/carrinhos/schema/DeleteCarrinhosConcluirCompraSchema.json"));
    }
}
