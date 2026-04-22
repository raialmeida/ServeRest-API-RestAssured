package services.produtos.tests;

import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.Hooks;
import services.produtos.requests.GetProdutosByIdRequest;
import utils.SchemaValidator;
import utils.UtilsProduto;
import io.qameta.allure.Feature;

@DisplayName("Produtos - GET /produtos/{_id}")
@Feature("Produtos")
public class GetProdutosByIdTest extends Hooks {

    @Test
    @DisplayName("Verificar consulta de produto por id retornando o mesmo id solicitado")
    public void validarGetProdutosPorIdComSucesso() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        GetProdutosByIdRequest.executar(idProduto)
                .assertThat()
                .statusCode(200)
                .body("_id", equalTo(idProduto));
    }

    @Test
    @DisplayName("Validar contrato da resposta de consulta de produto por id")
    public void validarSchemaGetProdutosPorId() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        GetProdutosByIdRequest.executar(idProduto)
                .assertThat()
                .statusCode(200)
                .body(SchemaValidator.matchesSchema("services/produtos/schema/GetProdutosByIdSchema.json"));
    }
}
