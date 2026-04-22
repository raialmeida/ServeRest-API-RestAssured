package services.produtos.tests;

import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.Hooks;
import services.produtos.payloads.PutProdutosByIdPayload;
import services.produtos.requests.PutProdutosByIdRequest;
import utils.SchemaValidator;
import utils.UtilsProduto;
import io.qameta.allure.Feature;

@DisplayName("Produtos - PUT /produtos/{_id}")
@Feature("Produtos")
public class PutProdutosByIdTest extends Hooks {

    @Test
    @DisplayName("Validar alteração de produto com sucesso retornando mensagem de confirmação")
    public void validarPutProdutosPorIdComSucesso() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        String payload = PutProdutosByIdPayload.payload();
        PutProdutosByIdRequest.executar(idProduto, payload)
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("Registro alterado com sucesso"));
    }

    @Test
    @DisplayName("Validar contrato da resposta de alteração de produto")
    public void validarSchemaPutProdutosPorId() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        String payload = PutProdutosByIdPayload.payload();
        PutProdutosByIdRequest.executar(idProduto, payload)
                .assertThat()
                .statusCode(200)
                .body(SchemaValidator.matchesSchema("services/produtos/schema/PutProdutosByIdSchema.json"));
    }
}
