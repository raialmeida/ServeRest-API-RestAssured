package br.com.serverest.services.produtos.tests;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.serverest.hooks.Hooks;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.TmsLink;
import br.com.serverest.services.produtos.requests.DeleteProdutosByIdRequest;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import br.com.serverest.utils.UtilsProduto;

@DisplayName("Produtos - DELETE /produtos/{_id}")
@Feature("Produtos")
public class DeleteProdutosByIdTest extends Hooks {

    @Test
    @TmsLink("226148")
    @Severity(CRITICAL)
    @DisplayName("Validar exclusão de produto com sucesso retornando mensagem de confirmação")
    public void validarDeleteProdutosPorIdComSucesso() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        DeleteProdutosByIdRequest.enviar(idProduto)
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("Registro excluído com sucesso"));
    }

    @Test
    @TmsLink("237950")
    @Severity(CRITICAL)
    @DisplayName("Validar contrato da resposta de exclusão de produto")
    public void validarSchemaDeleteProdutosPorId() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        DeleteProdutosByIdRequest.enviar(idProduto)
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("services/produtos/schema/DeleteProdutosByIdSchema.json"));
    }
}
