package services.carrinhos.tests;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import config.Hooks;
import services.carrinhos.payloads.PostCarrinhosPayload;
import services.carrinhos.requests.PostCarrinhosRequest;
import utils.SchemaValidator;
import utils.UtilsProduto;
import io.qameta.allure.Feature;

@DisplayName("Carrinhos - POST /carrinhos")
@Feature("Carrinhos")
public class PostCarrinhosTest extends Hooks {

    @Test
    @Tag("smoke")
    @DisplayName("Validar cadastro de carrinho com sucesso retornando mensagem e id")
    public void validarPostCarrinhosComSucesso() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        String payload = PostCarrinhosPayload.payload(idProduto);
        
        PostCarrinhosRequest.enviar(payload)
                .assertThat()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue());
    }

    @Test
    @DisplayName("Validar contrato da resposta de cadastro de carrinho")
    public void validarSchemaPostCarrinhos() {
        String idProduto = UtilsProduto.criarProduto().jsonPath().getString("_id");
        String payload = PostCarrinhosPayload.payload(idProduto);

        PostCarrinhosRequest.enviar(payload)
                .assertThat()
                .statusCode(201)
                .body(SchemaValidator.matchesSchema("services/carrinhos/schema/PostCarrinhosSchema.json"));
    }
}
