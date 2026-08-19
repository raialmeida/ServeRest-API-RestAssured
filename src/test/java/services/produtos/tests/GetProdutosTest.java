package services.produtos.tests;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.Hooks;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.TmsLink;
import services.produtos.requests.GetProdutosRequest;
import utils.SchemaValidator;

@DisplayName("Produtos - GET /produtos")
@Feature("Produtos")
public class GetProdutosTest extends Hooks {

    @Test
    @TmsLink("261439")
    @Severity(CRITICAL)
    @DisplayName("Verificar listagem de produtos retornando lista não nula")
    public void validarGetProdutosComSucesso() {

        GetProdutosRequest.enviar()
                .assertThat()
                .statusCode(200)
                .body("produtos", notNullValue());
    }

    @Test
    @TmsLink("272805")
    @Severity(CRITICAL)
    @DisplayName("Validar contrato da resposta de listagem de produtos")
    public void validarSchemaGetProdutos() {

        GetProdutosRequest.enviar()
                .assertThat()
                .statusCode(200)
                .body(SchemaValidator.matchesSchema("GetProdutosSchema.json"));
    }
}
