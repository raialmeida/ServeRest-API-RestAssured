package services.produtos.tests;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.Hooks;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.TmsLink;
import services.produtos.payloads.PostProdutosPayload;
import services.produtos.requests.PostProdutosRequest;
import utils.SchemaValidator;

@DisplayName("Produtos - POST /produtos")
@Feature("Produtos")
public class PostProdutosTest extends Hooks {

    @Test
    @TmsLink("283617")
    @Severity(CRITICAL)
    @DisplayName("Validar cadastro de produto com sucesso retornando mensagem e id")
    public void validarPostProdutosComSucesso() {
        String payload = PostProdutosPayload.payload();
        PostProdutosRequest.enviar(payload)
                .assertThat()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue());
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
                .body(SchemaValidator.matchesSchema("PostProdutosSchema.json"));
    }
}
