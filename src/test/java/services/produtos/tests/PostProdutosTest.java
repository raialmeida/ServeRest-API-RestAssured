package services.produtos.tests;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.Hooks;
import services.produtos.payloads.PostProdutosPayload;
import services.produtos.requests.PostProdutosRequest;
import utils.SchemaValidator;
import io.qameta.allure.Feature;

@DisplayName("Produtos - POST /produtos")
@Feature("Produtos")
public class PostProdutosTest extends Hooks {

    @Test
    @DisplayName("Validar cadastro de produto com sucesso retornando mensagem e id")
    public void validarPostProdutosComSucesso() {
        String payload = PostProdutosPayload.payload();
        PostProdutosRequest.executar(payload)
                .assertThat()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue());
    }

    @Test
    @DisplayName("Validar contrato da resposta de cadastro de produto")
    public void validarSchemaPostProdutos() {
        String payload = PostProdutosPayload.payload();
        PostProdutosRequest.executar(payload)
                .assertThat()
                .statusCode(201)
                .body(SchemaValidator.matchesSchema("services/produtos/schema/PostProdutosSchema.json"));
    }
}
