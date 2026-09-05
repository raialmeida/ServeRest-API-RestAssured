package br.com.serverest.services.produtos.tests;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.serverest.hooks.Hooks;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.TmsLink;
import br.com.serverest.services.produtos.requests.GetProdutosByIdRequest;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import br.com.serverest.utils.UtilsProduto;

@DisplayName("Produtos - GET /produtos/{_id}")
@Feature("Produtos")
public class GetProdutosByIdTest extends Hooks {

    @Test
    @TmsLink("248316")
    @Severity(CRITICAL)
    @DisplayName("Verificar consulta de produto por id retornando o mesmo id solicitado")
    public void validarGetProdutosPorIdComSucesso() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        GetProdutosByIdRequest.enviar(idProduto)
                .assertThat()
                .statusCode(200)
                .body("_id", equalTo(idProduto));
    }

    @Test
    @TmsLink("259784")
    @Severity(CRITICAL)
    @DisplayName("Validar contrato da resposta de consulta de produto por id")
    public void validarSchemaGetProdutosPorId() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        GetProdutosByIdRequest.enviar(idProduto)
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("services/produtos/schema/GetProdutosByIdSchema.json"));
    }
}
