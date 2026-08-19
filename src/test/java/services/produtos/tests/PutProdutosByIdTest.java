package services.produtos.tests;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.Hooks;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.TmsLink;
import services.produtos.payloads.PutProdutosByIdPayload;
import services.produtos.requests.PutProdutosByIdRequest;
import utils.SchemaValidator;
import utils.UtilsProduto;

@DisplayName("Produtos - PUT /produtos/{_id}")
@Feature("Produtos")
public class PutProdutosByIdTest extends Hooks {

    @Test
    @TmsLink("305729")
    @Severity(CRITICAL)
    @DisplayName("Validar alteração de produto com sucesso retornando mensagem de confirmação")
    public void validarPutProdutosPorIdComSucesso() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        String payload = PutProdutosByIdPayload.payload();
        PutProdutosByIdRequest.enviar(idProduto, payload)
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("Registro alterado com sucesso"));
    }

    @Test
    @TmsLink("316482")
    @Severity(CRITICAL)
    @DisplayName("Validar contrato da resposta de alteração de produto")
    public void validarSchemaPutProdutosPorId() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        String payload = PutProdutosByIdPayload.payload();
        PutProdutosByIdRequest.enviar(idProduto, payload)
                .assertThat()
                .statusCode(200)
                .body(SchemaValidator.matchesSchema("PutProdutosByIdSchema.json"));
    }
}
