package services.produtos.tests;

import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.Hooks;
import services.produtos.requests.DeleteProdutosByIdRequest;
import utils.SchemaValidator;
import utils.UtilsProduto;
import io.qameta.allure.Feature;

@DisplayName("Produtos - DELETE /produtos/{_id}")
@Feature("Produtos")
public class DeleteProdutosByIdTest extends Hooks {

    @Test
    @DisplayName("Validar exclusão de produto com sucesso retornando mensagem de confirmação")
    public void validarDeleteProdutosPorIdComSucesso() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        DeleteProdutosByIdRequest.enviar(idProduto)
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("Registro excluído com sucesso"));
    }

    @Test
    @DisplayName("Validar contrato da resposta de exclusão de produto")
    public void validarSchemaDeleteProdutosPorId() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        DeleteProdutosByIdRequest.enviar(idProduto)
                .assertThat()
                .statusCode(200)
                .body(SchemaValidator.matchesSchema("services/produtos/schema/DeleteProdutosByIdSchema.json"));
    }
}
