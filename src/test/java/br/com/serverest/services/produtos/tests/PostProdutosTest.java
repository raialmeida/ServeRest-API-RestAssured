package br.com.serverest.services.produtos.tests;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.serverest.config.Environment;
import br.com.serverest.database.DatabaseConfig;
import br.com.serverest.hooks.Hooks;
import br.com.serverest.services.produtos.payloads.PostProdutosPayload;
import br.com.serverest.services.produtos.requests.PostProdutosRequest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.TmsLink;
import io.restassured.path.json.JsonPath;

@DisplayName("Produtos - POST /produtos")
@Feature("Produtos")
public class PostProdutosTest extends Hooks {

    @Test
    @TmsLink("283617")
    @Severity(CRITICAL)
    @DisplayName("Validar cadastro de produto com sucesso retornando mensagem e id")
    public void validarPostProdutosComSucesso() {
        String payload = PostProdutosPayload.payload();
        String produtoId = PostProdutosRequest.enviar(payload)
                .assertThat()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue())
                .extract()
                .path("_id");

        // var produto = DatabaseConfig.sqlServerQuery(
        //         Environment.getEnv("SQLSERVER_DB_NAME"),
        //         "SELECT Id, Nome, Preco, Descricao, Quantidade FROM dbo.Produtos WHERE Id = ?",
        //         produtoId);

        // assertThat("O cadastro deve gravar um produto com o ID retornado pela API", produto, hasSize(1));
        // assertThat(produto.get(0).get("Id"), equalTo(produtoId));
        // assertThat(produto.get(0).get("Nome"), equalTo(JsonPath.from(payload).getString("nome")));
        // assertThat(produto.get(0).get("Preco"), equalTo(JsonPath.from(payload).getInt("preco")));
        // assertThat(produto.get(0).get("Descricao"), equalTo(JsonPath.from(payload).getString("descricao")));
        // assertThat(produto.get(0).get("Quantidade"), equalTo(JsonPath.from(payload).getInt("quantidade")));
    }

    @Test
    @TmsLink("294158")
    @Severity(CRITICAL)
    @DisplayName("Validar contrato da resposta de cadastro de produto")
    public void validarSchemaPostProdutos() {
        String payload = PostProdutosPayload.payload();
        PostProdutosRequest.enviar(payload)
                .assertThat()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("services/produtos/schema/PostProdutosSchema.json"));
    }
}
