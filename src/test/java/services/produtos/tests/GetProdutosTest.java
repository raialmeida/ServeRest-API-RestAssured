package services.produtos.tests;

import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.Hooks;
import services.produtos.requests.GetProdutosRequest;
import utils.SchemaValidator;
import io.qameta.allure.Feature;

@DisplayName("Produtos - GET /produtos")
@Feature("Produtos")
public class GetProdutosTest extends Hooks {

    @Test
    @DisplayName("Verificar listagem de produtos retornando lista não nula")
    public void validarGetProdutosComSucesso() {

        GetProdutosRequest.enviar()
                .assertThat()
                .statusCode(200)
                .body("produtos", notNullValue());
    }

    @Test
    @DisplayName("Validar contrato da resposta de listagem de produtos")
    public void validarSchemaGetProdutos() {

        GetProdutosRequest.enviar()
                .assertThat()
                .statusCode(200)
                .body(SchemaValidator.matchesSchema("services/produtos/schema/GetProdutosSchema.json"));
    }
}
